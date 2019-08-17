create table article
(
    article_id      varchar(32)                           not null comment '文章id'
        primary key,
    article_title   varchar(32)                           not null comment '文章标题',
    article_content text                                  null comment '文章内容',
    create_time     timestamp   default CURRENT_TIMESTAMP not null comment '文章发布时间',
    modify_time     timestamp   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '文章最后修改时间',
    article_visible tinyint     default 0                 null comment '0-可见，1-不可见，默认为其他人可见',
    article_like    int         default 0                 null comment '文章点赞(喜欢)数',
    author_id       varchar(20)                           not null comment '作者id',
    author_name     varchar(16)                           not null comment '作者昵称',
    category_id     varchar(10) default '0'               not null comment '0-默认类目，初始生成得必须有得一个分类',
    category_name   varchar(16) default '默认'              not null comment '类别名称，默认的目录名称为默认'
);

create index author_id
    on article (author_id);

create table article_category
(
    category_id   varchar(10)                         not null comment '类目id'
        primary key,
    category_name varchar(16)                         null comment '类目名称',
    author_id     varchar(20)                         null comment '用户id',
    create_time   timestamp default CURRENT_TIMESTAMP not null comment '类目创建时间'
);

create index author_id
    on article_category (author_id);

create table author
(
    author_id         varchar(20)                           not null comment '用户id'
        primary key,
    author_name       varchar(16)                           not null comment '用户昵称',
    author_account    varchar(32)                           not null comment '用户账号',
    author_password   varchar(64)                           not null comment '用户密码',
    author_gender     tinyint     default 0                 null comment '0-空,1-男,2-女,默认为空',
    author_birthday   timestamp   default CURRENT_TIMESTAMP not null comment '出生日期',
    author_avatar     varchar(64) default 'null'            null comment '用户头像,默认为空',
    author_permission tinyint     default 0                 null comment '0-普通，1-管理员，默认为普通用户',
    create_time       timestamp   default CURRENT_TIMESTAMP not null comment '用户注册时间',
    constraint author_account
        unique (author_account),
    constraint author_name
        unique (author_name)
);

create table author_follow
(
    author_id   varchar(20) null comment '用户id',
    follow_id   varchar(20) null comment '被关注的用户id',
    follow_name varchar(16) not null comment '被关注用户昵称'
);

create index author_id
    on author_follow (author_id);

create table author_login
(
    author_id  varchar(20)                         null comment '用户id',
    login_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后登录时间',
    login_ip   varchar(16)                         null comment '最后登录ip',
    constraint author_id
        unique (author_id)
);

create index author_id_2
    on author_login (author_id);


