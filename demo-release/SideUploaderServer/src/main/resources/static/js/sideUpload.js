// 开启页面时加载下拉框
(function () {
    let element = layui.element;
    let form = layui.form;
    window.onload = function (){
        $.ajax({
            url: "http://wsswms.xyz:8080/getList",
            //url: "http://localhost:8080/getList",
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
})();

// 模拟点击实际上传按钮
$('#upload-button').click(function () {
    $('#fileUploadInput').click();
});

// 监听文件选择
$('input[id=fileUploadInput]').change(function () {
    let file = $('#fileUploadInput').val();
    let filename = getFileName(file);
    let text = $('#upload-button-text')
    text.html("<p id=\"upload-button-text\" style=\"font-size:20px; color: #999;\">点击选择脚本文件</p>");
    text.html("<p id=\"upload-button-text\" style=\"font-size:20px; color: #009688;\">"+ filename +"</p>");

    function getFileName(file) {
        let pos = file.lastIndexOf("\\");
        return file.substring(pos + 1);
    }
})

// 上传文件function
function uploadSideFile(){
    const formData = new FormData($("#upload-form")[0]);
    const options = $("#projects-select option:selected");
    formData.append("pName", options.val());
    $.ajax({
        url: 'http://wsswms.xyz:8080/uploadFile',
        //url: 'http://localhost:8080/uploadFile',
        type: 'POST',
        data: formData,
        async: true,
        cache: false,
        contentType: false,
        processData: false,
        success: function () {
            alert("上传成功")
            parent.location.reload();
        },
        error: function (msg) {
            alert("上传失败")
        }
    });
}

// 按钮触发事件 判断文件不为空
function uploadButtonClick() {
    let file = $('#fileUploadInput').val();
    if(file.length == 0){
        alert("请选择一个脚本文件")
        $('#upload-button-text').html("<p id=\"upload-button-text\" style=\"font-size:20px; color: #999;\">( ^ω^)</p>");
    }else{
        uploadSideFile();
    }
}
