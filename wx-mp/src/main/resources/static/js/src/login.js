layui.use(['form', 'layer'], function () {
    var form = layui.form
        , layer = layui.layer;
    $('#login').keydown(function (e) {
        if (e.which == 13) {
            $("#fshLogin").trigger("click");
        }
    });
    //监听提交
    $('#fshLogin').click(function () {
        var username = $.trim($("#username").val());
        var password = $.trim($("#password").val());
        if(username==""){
            layer.msg('请输入账号');
            return;
        }
        if(password==""){
            layer.msg('请输入密码');
            return;
        }
        $.ajax({
            url: '/login',
            type: 'POST',
            dataType: 'json',
            data: {
                account: username,
                pwd: password
            },
            success: function (result) {
                if (result.success) {
                    window.location.href = "/admin/index.html";
                }else{
                    layer.msg(result.msg || "登录失败");
                }
            },
            error: function () {
                layer.msg("登录失败");
            }
        })
    })
});