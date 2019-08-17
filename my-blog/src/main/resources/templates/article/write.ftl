<html lang="zh">
<head>
    <title>写文章</title>
    <link rel="stylesheet" href="/editor/css/editormd.css"/>
    <script src="https://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="/editor/editormd.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <script src="//cdn.bootcss.com/qs/6.5.2/qs.min.js"></script>

    <#include "../common/nav-common.ftl"/>
    <style>

        body {
            margin: 0;
        }

        #title-div {
            position: relative;
            height: 70px;
        }
        #title-input {
            position: absolute;
            height: 50px;
            margin: 10px 0;
            width: 70%;
            left: 50%;
            /*transform: translate(auto, -50%);*/
            transform: translate(-50%, 0);
            padding: 3px 8px;
            font-weight: bold;
            font-size: 25px;
            border: 2px solid gray;
            border-radius: 10px;
        }
    </style>
</head>
<body>

<#--<div class="operate-nav"><button id="submit">提交</button></div>-->
<div id="title-div">
    <input id="title-input" placeholder="请输入标题">
</div>
<div class="editor-div">
    <div id="editor">
        <textarea id="editor-content" name="editor" style="display:none;"></textarea>
    </div>
</div>

<script>


    $(function () {

        let qs = window.Qs;
        axios.post('/category/find/all',
            qs.stringify({'authorId': 15626630})
        )
            .then(function (response) {
                console.log(response.data);
            })
            .catch(function (e) {
                console.log(e);
            });

        let editor;

        let save = document.getElementById("save");
        // save.onclick = function() {
        //
        // };

        editor = editormd("editor", {
            width: "70%",
            height: 640,
            syncScrolling: "single",
            path: "/editor/lib/",
            saveHTMLToTextarea: true,

            // 上传图片相关配置如下
            // 期待的上传图片成功后返回的 json格式
            // {
            //     success : 0 | 1, //0表示上传失败;1表示上传成功
            //         message : "提示的信息",
            //     url     : "图片地址" //上传成功时才返回
            // }
            imageUpload: true,
            imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            // 后端的上传图片的服务器地址
            imageUploadURL: "/server/upload/pic",

            emoji: true,
            toolbarIcons: function () {
                return ["save","|","undo","redo","|","bold","del","italic",
                    "|","h1","h2","h3","|","list-ul","list-ol","hr","|","link","image",
                    "code-block","table","emoji","html-entities",
                    "|","watch","preview","fullscreen","clear","search","help"];
            },
            toolbarCustomIcons: {
                save: "<button id='save'>保存</button>"
            },
            onload: function () {
                $("[id=\"save\"]").bind("click", function () {
                    console.log("开始上传文件");
                    let articleId = null;
                    let articleTitle = $("#title-input").val();
                    let articleVisible = 1;
                    let authorId = "15626630";
                    let categoryId = "1";
                    let articleContent = editor.getMarkdown();
                    console.log(articleTitle);
                    let jsonData = JSON.stringify({
                        "articleId": articleId,
                        "articleTitle": articleTitle,
                        "articleVisible": articleVisible,
                        "authorId": authorId,
                        "categoryId": categoryId,
                        "articleContent": articleContent});
                    $.ajax({
                        type: "POST",
                        url: "/article/write",
                        contentType: "application/json",
                        dataType: "json",
                        data: jsonData,
                        success: function (data) {
                            console.log(data);
                        },
                        error: function (XMLHttpRequest, textStatus) {
                            console.log("错误");

                        }
                    });
                })
            }
        });
    });
</script>
</body>
</html>