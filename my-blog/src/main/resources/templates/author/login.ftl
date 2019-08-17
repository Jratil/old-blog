<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="-UTF-8">
    <title>登录</title>
</head>
<link type="text/css" rel="stylesheet" href="/layui/css/layui.css"/>
<script src="/layui/layui.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="//cdn.bootcss.com/qs/6.5.2/qs.min.js"></script>

<style>

    .bg-img {
        position: absolute;
        width: 100%;
        height: 100%;
        filter: blur(4px);
        background-image: url("/images/login_bg.jpg");
        background-repeat: no-repeat;
        background-size: cover;
    }

    .form-div {
        position: absolute;
        width: 400px;
        height: 400px;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        border: 1px solid #2a2a2a;
        background: rgba(117, 244, 226, 0.04);
        box-shadow: 8px 8px 5px rgba(0, 0, 0, 0.24);
        border-radius: 6px;
    }

    .login-input-title {
        position: absolute;
        font-size: 40px;
        color: #00b8d8;
        margin-top: 20px;
        width: 100%;
        text-align: center;
        border-bottom: 1px solid #2a2a2a;
    }

    .form-content {
        position: relative;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }

    .layui-form-item {
        margin-top: 30px;
    }

    .input-label {
        font-size: 20px;
        color: #00b8d8;
    }

    .register-input {
        width: 200px;
    }

    .button-register {
        background: #00b8d8;
        width: 100px;
    }

    .layui-btn, .button-register, .register-input {
        box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.24);
        border-radius: 6px;
    }

    #register {
        margin: 20px 0 0 110px;
        width: 100px;
    }

</style>
<body>

<div class="bg-img"></div>

<div class="form-div">

    <div class="login-input-title">
        登录
    </div>

    <div class="form-content">
        <form class="layui-form" onsubmit="return false;">

            <div class="layui-form-item">
                <label class="layui-form-label input-label" id="email-input">邮箱</label>
                <div class="layui-input-inline">
                    <input class="layui-input register-input email-input" type="text" name="email"
                           lay-verType="tips"
                           lay-verify="email"
                           placeholder="请输入邮箱">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label input-label">密码</label>
                <div class="layui-input-inline">
                    <input class="layui-input register-input password-input" type="password" name="password"
                           lay-verType="tips"
                           lay-verify="required"
                           placeholder="请输入密码">
                </div>
                <#--                <div class="layui-form-mid layui-word-aux">辅助文字</div>-->
            </div>

            <div>
                <div class="layui-input-block">
                    <button class="layui-btn button-register" lay-submit lay-filter="login">登录</button>
                </div>
            </div>
        </form>
        <button class="layui-btn layui-btn-warm" id="register" onclick="toRegister()">注册</button>

    </div>
</div>

<script>

    layui.use(['form', 'layer'], function () {
        let form = layui.form,
        layer = layui.layer;

        form.on('submit(login)', function (data) {
            let formDate = data.field;
            let qs = window.Qs;

            axios.post('/author/login',
                qs.stringify({'account': formDate.email, 'password': formDate.password})
            )
                .then(function (response) {
                    let data = response.data;
                    if (data.code === 0) {
                        window.location.href = '/';
                    } else {
                        layer.tips(data.message, '.button-login');
                    }
                })
                .catch(function (e) {
                    console.log(e);
                });

            return false;
        });

    });


    function toRegister() {
        window.location.href = '/register';
    }

</script>
</body>
</html>