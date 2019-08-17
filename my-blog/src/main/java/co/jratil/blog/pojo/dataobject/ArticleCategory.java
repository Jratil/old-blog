package co.jratil.blog.pojo.dataobject;

import co.jratil.blog.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCategory implements Serializable {

    private static final long serialVersionUID = 329736111792845096L;

    /**
     * 类目id
     * 0：默认自动生成的名为“默认”的类目
     * 其他新建类目id在 0 上自增
     */
    private String categoryId;

    /**
     * 类目的名称
     */
    private String categoryName;

    /**
     * 作者的id
     */
    private String authorId;

    /**
     * 类目创建时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
}