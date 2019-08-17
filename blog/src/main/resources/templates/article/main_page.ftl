<html lang="zh">
<head>
    <title>主页</title>

    <link type="text/css" rel="stylesheet" href="/layui/css/layui.css"/>
    <link type="text/css" rel="stylesheet" href="/editor/css/editormd.css"/>
    <link rel="shortcut icon" href="https://pandao.github.io/editor.md/favicon.ico" type="image/x-icon"/>
    <script src="https://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="/layui/layui.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <script src="//cdn.bootcss.com/qs/6.5.2/qs.min.js"></script>
    <script src="/editor/lib/marked.min.js"></script>
    <script src="/editor/lib/prettify.min.js"></script>
    <script src="/editor/lib/raphael.min.js"></script>
    <script src="/editor/lib/underscore.min.js"></script>
    <script src="/editor/lib/sequence-diagram.min.js"></script>
    <script src="/editor/lib/flowchart.min.js"></script>
    <script src="/editor/editormd.min.js"></script>
</head>
<body>

<div>

    <div id="content">hello</div>
    <div id="layout" class="editor">
        <div id="editor">
            <textarea></textarea>
        </div>
    </div>

    <script>
        // window.location.href = "/write";
    </script>
    <script>


        let authorId = "15626630";
        window.onload = function () {
            let qs = window.Qs;
            axios.get('/article/main_page', {}).then(response => {
                let data = response.data;
                console.log(data);
                if (data.code === 0) {

                    // 需要将所有文件遍历解析出来
                    let content = document.getElementById('content');
                    let str = "一共有" + data.data.length + "条数据";
                    content.innerText = str + data.data;
                    console.log(data.data[1]);
                    let editor = editormd.markdownToHTML("editor", {
                        markdown        : data.data[1].articleContent,
                        htmlDecode      : "style,script,iframe",  // you can filter tags decode
                        emoji           : true,
                        taskList        : true,
                        tex             : true,  // 默认不解析
                        // flowChart       : true,  // 默认不解析
                        sequenceDiagram : true,  // 默认不解析
                    });
                } else if (data.code === 15) {
                    location.href = '/login?redirect="/"';
                }
            }).catch(e => {
                console.log(e);
            });
        }

    </script>

</div>
</body>
</html>
