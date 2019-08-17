package co.jratil.blog.service.impl;

import co.jratil.blog.enums.ResultEnum;
import co.jratil.blog.enums.UserRoleEnum;
import co.jratil.blog.mapper.AuthorLoginMapper;
import co.jratil.blog.mapper.AuthorMapper;
import co.jratil.blog.pojo.dataobject.Author;
import co.jratil.blog.pojo.dataobject.AuthorLogin;
import co.jratil.blog.pojo.dataobject.UserRole;
import co.jratil.blog.pojo.dto.AuthorDTO;
import co.jratil.blog.pojo.form.AuthorForm;
import co.jratil.blog.service.AuthorService;
import co.jratil.blog.service.RedisService;
import co.jratil.blog.service.UserRoleService;
import co.jratil.blog.utils.IdGenerateUtil;
import co.jratil.blog.utils.MD5Util;
import co.jratil.blog.constant.RedisConstant;
import co.jratil.blog.exception.AuthorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private AuthorLoginMapper loginMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 根据authorId查询用户
     *
     * @param authorId 用户id
     * @return 用户信息
     */
    @Cacheable(value = "author", key = "'authorId_'+#authorId")
    @Override
    public Author findAuthorById(String authorId) {
        Author author = authorMapper.findAuthorById(authorId);
        if (author == null) {
            log.error("【用户处理】查询用户出错，用户不存在，authorId={}", authorId);
            throw new AuthorException(ResultEnum.AUTHOR_NOT_EXIST);
        }

        return author;
    }

    /**
     * 根据账号查询用户
     *
     * @param account
     * @return
     */
    @Cacheable(value = "author", key = "'account_'+#account")
    @Override
    public Author findAuthorByAccount(String account) {
        Author author = authorMapper.findAuthorByAccount(account);
        if (author == null) {
            log.error("【用户处理】查询用户出错，用户不存在，account={}", account);
            throw new AuthorException(ResultEnum.AUTHOR_NOT_EXIST);
        }
        return author;
    }

    @Override
    public List<Author> findAllAuthor() {
        List<Author> authorList = authorMapper.findAllAuthor();
        if (CollectionUtils.isEmpty(authorList)) {
            log.error("【用户处理】查询用户出错，没有用户");
            throw new AuthorException(ResultEnum.AUTHOR_NOT_EXIST);
        }

        return authorList;
    }

    @Transactional
    @Override
    public void deleteAuthor(String account) {
        int result = authorMapper.deleteAuthor(account);
        if (result < 1) {
            log.error("【用户处理】删除用户出错，用户不存在，account={}", account);
            throw new AuthorException(ResultEnum.AUTHOR_NOT_EXIST);
        }
    }

    @Transactional
    @Override
    public AuthorDTO login(String account, String password, String ipAddr) {

        Author author = findAuthorByAccount(account);
        if (!MD5Util.md5Str(password).equals(author.getAuthorPassword())) {
            log.error("【用户处理】登录失败，密码错误，account={},password={}", account, password);
            throw new AuthorException(ResultEnum.PASSWORD_ERROR);
        }

        // 更新用户登录的 ip 地址和时间
        AuthorLogin authorLogin = new AuthorLogin();
        authorLogin.setAuthorId(author.getAuthorId());
        authorLogin.setLoginIp(ipAddr);
        loginMapper.insertOrUpdate(authorLogin);

        AuthorDTO authorDTO = new AuthorDTO();
        BeanUtils.copyProperties(author, authorDTO);
        return authorDTO;
    }

    @Transactional
    @Override
    public boolean register(AuthorForm authorForm) {

        String account = authorForm.getAuthorAccount();
        // 判断验证码是否正确
        verifyCode(account, authorForm.getVerifyCode());

        Author author = new Author();
        author.setAuthorName(authorForm.getAuthorName());
        author.setAuthorAccount(account);
        author.setAuthorPassword(MD5Util.md5Str(authorForm.getAuthorPassword()));
        author.setAuthorId(IdGenerateUtil.generateId(RedisConstant.AUTHOR_ID));

        UserRole userRole = new UserRole();
        userRole.setAuthorId(author.getAuthorId());
        userRole.setRole(UserRoleEnum.ROLE_USER.getUserRole());
        userRoleService.addRole(userRole);

        int result = authorMapper.addAuthorSelective(author);

        // 数据库插入使用了ignore, 所以出现相同的会忽略
        if (result != 1) {
            log.error("【用户处理】注册失败，账号或昵称可能已存在，name={},account={}", authorForm.getAuthorName(), authorForm.getAuthorAccount());
            throw new AuthorException(ResultEnum.AUTHOR_EXIST);
        }
        return true;
    }

    @CacheEvict(value = "author", key = "'account_'+#authorDTO.authorAccount")
    @Transactional
    @Override
    public boolean updateAuthor(AuthorDTO authorDTO) {

        Author author = authorMapper.findAuthorByAccount(authorDTO.getAuthorAccount());
        if (author == null) {
            log.error("【用户处理】更新资料失败，用户不存在，account={}", authorDTO.getAuthorAccount());
            throw new AuthorException(ResultEnum.AUTHOR_NOT_EXIST);
        }

        BeanUtils.copyProperties(authorDTO, author);

        authorMapper.updateByAuthorSelective(author);
        return true;
    }

    @CacheEvict(value = "author", key = "'account_'+#authorForm.authorAccount")
    @Transactional
    @Override
    public boolean forgetPassword(AuthorForm authorForm) {

        String account = authorForm.getAuthorAccount();
        // 判断验证码是否正确
        verifyCode(account, authorForm.getVerifyCode());

        Author author = authorMapper.findAuthorByAccount(account);
        if (author == null) {
            log.error("【用户处理】忘记密码失败，用户不存在，account={}", account);
            throw new AuthorException(ResultEnum.AUTHOR_NOT_EXIST);
        }

        author.setAuthorPassword(MD5Util.md5Str(authorForm.getAuthorPassword()));
        authorMapper.updateByAuthorSelective(author);

        return true;
    }

    /**
     * 判断验证码是否正确
     *
     * @param account    账户，用来从redis中获取到该账户的验证码
     * @param verifyCode 用户输入的验证码
     */
    private void verifyCode(String account, String verifyCode) {

        //TODO----验证码开关代码

        /*if (!verifyCode.equals(redisService.get(EmailConstant.REDIS_VERIFY_CODE_KEY + account))) {
            log.error("【用户注册】验证码不正确，verifyCode={}", verifyCode);
            throw new AuthorException(ResultEnum.VERIFY_NOT_EQUAL);
        }*/
    }
}
