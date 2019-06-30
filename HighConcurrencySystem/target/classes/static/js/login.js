/**
 * Created by mcorleon 2019/2/21.
 */
$(function () {
    layui.use(['layer', 'form'], function () {
        var form = layui.form,
            layer = layui.layer;
        //表单
        form.render();
        //监听提交
        form.on('submit(login)', function(data){
            var uid=$("#uid").val();
            var psw=$("#psw").val();
            var index=layer.load(1);
            $.ajax({
                type: "post",
                url: "/login",
                data:{'uid':uid,'psw':psw},
                dataType: "json",
                success: function (data) {

                    if(data.code==200){
                        window.location.href=data.msg;
                    }else {
                        layer.msg(data.msg);
                    }
                    layer.close(index);

                },
                error: function () {
                    console.log("ajax error");
                    layer.close(index);
                }
            });

            return false;
        });
    });
})