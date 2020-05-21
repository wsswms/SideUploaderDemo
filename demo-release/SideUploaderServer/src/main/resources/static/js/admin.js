(function () {
    let element = layui.element;
    let form = layui.form;

    element.on('tab(admin-top)', function(data){
        // 切换到分数查询页面时加载下拉框
        if(data.index == 1){
            $.ajax({
                url: "/getList",
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                traditional: "true",
                success: function (data) {
                    let select = $("#projects-select");
                    select.find("option:selected").text("");
                    select.empty();
                    const jsonArray = eval(data);
                    for (let i = 0; i < jsonArray.length; i ++) {
                        const jsonObj = data[i];
                        select.append("<option value='" + jsonObj.projectName + "'>" + jsonObj.projectName + "</option>");
                    }
                    form.render();
                },
                error: function (msg) {
                    alert("数据加载出错！")
                }
            });
        }
    });
})();

function createProject() {
    let param = $("#create-form").serialize();
    let pName = $("input[name='pName']").val();
    let pUrl = $("input[name='pUrl']").val();
    if(pName.replace(/(^s*)|(s*$)/g, "").length ==0 ||
        pUrl.replace(/(^s*)|(s*$)/g, "").length ==0){
        alert('不能留空');
    }else{
        $.ajax({
            url: "/createProject",
            type: "POST",
            dataType: "json",
            data: param,
            success: function (response,status,xhr) {
                if (xhr.status == 200){
                    alert("创建新项目"+ response.projectName +"成功");
                    parent.location.reload();
                }
            },
            error: function (response) {
                alert("创建新项目失败" + response.message);
                parent.location.reload();
            }
        });
    }
}

// 加载第一页项目列表
window.onload = function () {
    const table = $('#project-table');
    $.ajax({
        url: "/getList",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        traditional: "true",
        success: function (data) {
            const jsonArray = eval(data);
            for (let i = 0; i < jsonArray.length; i ++) {
                const jsonObj = jsonArray[i];
                table.append("<tr><td>" + jsonObj.projectName + "</td><td>" + jsonObj.projectUrl + "</td></tr>");
            }
        },
        error: function (msg) {
            table.append("<tr><td>没有数据</td><td></td></tr>");
        },
    });
}

// 计算分数并填入表格
function getScore() {
    let param = $("#score-form").serialize();
    let info = $('#score-info')
    let table = $('#student-scores');
    $.ajax({
        url: "/getScore",
        type: "POST",
        dataType: "json",
        data: param,
        success: function (data) {
            const jsonArray = eval(data);
            const students = jsonArray.students;
            $('#student-scores tr:not(:first)').html("");
            $('#score-info tr:not(:first)').html("");
            for (let i = 0; i < students.length; i ++) {
                const jsonObj = students[i];
                table.append("<tr><td>" + jsonObj.name + "</td><td>" + jsonObj.score + "</td></tr>");
            }
            info.append("<tr><td>" + jsonArray.url + "</td></tr>");
        },
        error: function (msg) {
            $('#student-scores tr:not(:first)').html("");
            $('#score-info tr:not(:first)').html("");
            info.append("<tr><td>没有数据</td></tr>");
            table.append("<tr><td>没有数据</td><td></td></tr>");
        }
    });
}

// 清空表格
function cleanTable() {
    $('#student-scores tr:not(:first)').html("");
    $('#score-info tr:not(:first)').html("");

}

