<html>
<head>
    <title>注册</title>
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
            height: 500px;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            border: 1px solid #2a2a2a;
            background: rgba(244, 243, 236, 0.27);
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

        .verify-input {
            width: 100px;
            margin-right: 0px;
        }

        .layui-btn, .button-register, .layui-input, .get-code {
            box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.24);
            border-radius: 6px;
        }

        .get-code {
            height: 30px;
        }

        .button-group {
            margin: 20px 0 0 110px;
        }

        .button-register {
            background: #00b8d8;
        }

        #login, .button-register, .get-code {
            width: 80px;
        }
    </style>
</head>

<body>

<div class="bg-img"></div>

<div class="form-div">

    <div class="login-input-title">
        注册
    </div>

    <div class="form-content">
        <form class="layui-form" onsubmit="return false;">

            <div class="layui-form-item">
                <label class="layui-form-label input-label" id="name-input">昵称</label>
                <div class="layui-input-inline">
                    <input class="layui-input register-input email-input" type="text" name="name"
                           lay-verType="tips"
                           lay-verify="name"
                           placeholder="请输入昵称">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label input-label">邮箱</label>
                <div class="layui-input-inline">
                    <input class="layui-input register-input email-input" id="email-input" type="text" name="email"
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
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label input-label">确认密码</label>
                <div class="layui-input-inline">
                    <input class="layui-input register-input password-input-repeat" id="repeat-password" type="password"
                           name="repeat_password"
                           lay-verType="tips"
                           lay-verify="required"
                           placeholder="请再输入密码">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label input-label">验证码</label>
                <div class="layui-input-inline">
                    <input class="layui-input verify-input" id="verify-code" type="text" name="verify_code"
                           lay-verType="tips"
                           lay-verify="number"
                           placeholder="验证码">

                    <button class="layui-btn get-code" id="get-code" onclick="sendVerify()">获取</button>
                </div>
            </div>

            <div class="button-group">
                <button class="layui-btn button-register" lay-filter="register" lay-submit>注册</button>
                <button class="layui-btn layui-btn-warm" id="login" onclick="toLogin()">登录</button>
            </div>
        </form>


    </div>
</div>

<script>

    let sendVerify;

    layui.use(['layer', 'form', 'util'], function () {
        let layer = layui.layer,
            form = layui.form,
            util = layui.util,
            qs = window.Qs;

        form.verify({
            name: [
                /[a-zA-Z_0-9\u4e00-\u9fa5=+\-*/()%]{2,15}/,
                '昵称只能3-15位的字母数字中文_=+-*/()%组成'
            ]
        });

        form.on('submit(register)', function (data) {
            let formData = data.field;

            if (formData.password !== formData.repeat_password) {
                layer.tips('两次输入的密码不一致', '#repeat-password');
            }

            axios({
                method: 'post',
                url: '/author/register',
                data: {
                    'name': formData.name,
                    'account': formData.email,
                    'password': formData.password,
                    'verify-code': formData.verify_code
                },
                headers: {'Content-type': 'application/json'}

            })
                .then(function (response) {
                    let data = response.data;
                    if (data.code === 0) {
                        layer.msg("注册成功，正在跳转...");
                        setTimeout(function () {
                            window.location.href = "/";
                        }, 1000);
                    } else {
                        layer.tips(data.message, '.button-register');
                    }
                })
                .catch(function (e) {
                    console.log(e)
                });

            return false;
        });

        sendVerify = function sendVerifyCode() {
            let email = document.getElementById('email-input');
            axios.post("/api/email/send_code",
                qs.stringify({'email': email.value})
            )
                .then(function (response) {
                    let data = response.data;

                    if (data.code === 0) {
                        let nowTime = new Date().getTime();
                        let endTime = nowTime + 60 * 1000;
                        let getBtn = document.getElementById('get-code');

                        util.countdown(endTime, nowTime, function (date, serverTime, timer) {
                            let sec = date[2] * 60 + date[3];
                            getBtn.innerText = sec === 0 ? '获取' : sec;
                            if (sec === 0) {
                                getBtn.innerText = '获取';
                                getBtn.classList.remove('layui-btn-disabled');
                            } else {
                                getBtn.innerText = sec;
                                getBtn.classList.add('layui-btn-disabled');
                            }
                        });
                    } else {
                        layer.tips('验证码发送失败', '.get-code');
                    }
                })
                .catch(function (e) {
                    console.log(e);
                });
        }

    });


    function toLogin() {
        window.location.href = '/login';
    }

</script>
</body>

</html>