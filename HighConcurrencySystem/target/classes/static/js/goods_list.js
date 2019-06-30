/**
 * Created by mcorleon 2019/2/21.
 */
$(function () {
    //获取当前session下的用户
    $.ajax({
        type: "get",
        url: "/user/getCurrentUser",
        data:{},
        dataType: "json",
        success: function (data) {

            if(data==null){
                window.location.href="/login";
            }else {
                console.log(data);
                $("#p1").text("欢迎,"+data.nickname);
            }
        },
        error: function () {
            console.log("ajax error");
            window.location.href = "/login";
        }
    });
    layui.use(['layer', 'form','table'], function () {
        var form = layui.form,
            layer = layui.layer;
            table = layui.table;
        //表单
        form.render();
        table.render({
            elem: '#tb',
            url: '/goods/getGoodsList',
            // even: true,
            limits:[5,10],
            // where: {start_time:start_time ,over_time: over_time, fault_state: state}
            parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.count, //解析数据长度
                    "data": res.data //解析数据列表
                }
            }
            , cols: [[
                {field: 'id', title: '商品编号', width: 180, align: 'center'}
                , {field: 'name', title: '商品名称', width: 150, align: 'center'}
                , {field: 'title', width: 450, title: '标题', align: 'center'}
                ,{field: 'img', title: '图片', width: 200,align: 'center',templet:'<div><img src="{{d.img}}"></div>'}
                , {field: 'detail', title: '详情', width: 180, align: 'center'}
                , {field: 'price', title: '价格',width: 100, align: 'center'}
                , {field: 'stock', width: 100, title: '库存', align: 'center'}
                , { title: '操作', toolbar: '#barDemo', width: 101,align: 'center'}
            ]]
            , page: true
        });
        table.on('tool(tb)', function (obj) {
            var data = obj.data;
            console.log(obj.data);
            if (obj.event === 'view') {
                window.open("/miaoshaGoods?id=" + data.id, '_blank');
            }
        });

    });
})


