var data = [];
function render() {
	$.ajax({
		type : "get",
		url : fyToolUrl,
		// data: "para="+para, 此处data可以为 a=1&b=2类型的字符串 或 json数据。
		data : {
			action : "getTypes"
		},
		cache : false,
		async : false,
		dataType : "json",
		success : function(result, textStatus, jqXHR) {
			if (result.msg != null) { // 错误信息
				alert(result.msg);
				console.log(result.msg);
			} else { // 成功
				if(result.total === 0){
					$("#hasCatagory").show();
				}else{
					$("#hasCatagory").hide();
					data = result.types;
					$('.catagory-list').html('');
					for (var i = 0, len = data.length; i < len; i++) {
						if (data[i].catagory_name == '其他') {
							$('.catagory-list').append(
									'<li data-index="' + i + '" data-id="' + data[i].id + '"><span>'
									+ data[i].catagory_name
									+ '</span><div class="control-buttons"><button class="check">查看</button></li>');
						} else {
							$('.catagory-list').append(
									'<li data-index="'
									+ i
									+ '" data-id="'
									+ data[i].id
									+ '"><span>'
									+ data[i].catagory_name
									+ '</span><div class="control-buttons"><button class="check">查看</button><button class="modify">修改</button><button class="delete">删除</button></div></li>');
						}
					}
					$('.check').click(handleOnCheck);
					$('.modify').click(handleOnModify);
					$('.delete').click(handleOnDelete);
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("请求失败！");
			return false;
		}
	});

}
// 初始化界面
render();

// 点击查看
function handleOnCheck() {
	var index = $(this).parent().parent().attr('data-index');
	$('.cover').fadeIn();
	$('.catagory-information').fadeIn().html(
			'<button id="close-btn"></button><table><tr><td>类型：</td><td>'
					+ data[index].catagory_name + '</td></tr></table>');
	// 点击关闭按钮
	$('#close-btn').unbind().click(function() {
		$('.catagory-information').fadeOut(100).html('');
		$('.cover').fadeOut();
	});
	// 点击遮布
	$('.cover').unbind().click(function() {
		$('.catagory-information').fadeOut(100).html('');
		$('.cover').fadeOut();
	});
};

// 点击修改
function handleOnModify() {
	var index = $(this).parent().parent().attr('data-index');
	var type_id = $(this).parent().parent().attr('data-id');
	var before = data[index].catagory_name;
	$('.cover').fadeIn();
	$('.catagory-modify')
			.fadeIn()
			.html(
					'<button id="close-btn"></button><form class="modify-form"><label for="type-name">类型：</label><input id="type" name="type-name" type="text" value="'
							+ data[index].catagory_name
							+ '"><button type="submit">提交</button><from>');
	$('.modify-form').submit(function() {
		var catagory = $('#type').val();
		console.log(catagory);
		if (catagory != null && catagory != before) {
			if ($.trim(catagory) === '') {
				alert('请输入一个有效值，不能为空或空格！');
				$('#type').focus();
				return false;
			}
			$.ajax({
				type : "post",
				url : fyToolUrl,
				data : {
					"action" : "updateType",
					"catagoryName" : catagory,
					"id" : type_id
				},
				cache : false,
				async : false,
				dataType : "json",
				context : document.body,
				success : function(result) {
					if (result.result != null) {
						if (result.result == true) {
							alert(result.msg);
							render();
						} else if(result.result == false && result.total > 0){
							console.log(result);
							alert("该类型 [" + catagory + "] 已存在，请勿重复添加！");
						}
					} else {
						console.log(result);
						alert("修改失败,请刷新后重试！");
						render();
					}
				},
				error : function(result) {
					console.log(result);
					alert("修改失败,请刷新后重试！");
					render();
				}
			});
		} else {
			alert("未更改原始数据！");
		}

		$('.catagory-modify').fadeOut(100).html('');
		$('.cover').fadeOut();
	});
	// 点击关闭按钮
	$('#close-btn').unbind().click(function() {
		$('.catagory-modify').fadeOut(100).html('');
		$('.cover').fadeOut();
	});
	// 点击遮布
	$('.cover').unbind().click(function() {
		$('.catagory-modify').fadeOut(100).html('');
		$('.cover').fadeOut();
	});
};

// 点击删除
var data_id;
function handleOnDelete() {
	var index = $(this).parent().parent().attr('data-index');
	console.log(index);
    var name = $(this).parent().parent().children("span").html();
    $("#catagory_span").html(name);
	data_id = $(this).parent().parent().attr('data-id');
	$('.cover').fadeIn();
	$('.delete-prompt').fadeIn();
	
	$('#yes').click({id: data_id, name: name},handleOnDeleteYes);
	// 点击确认
	/*
	 * $('#yes').unbind().click(function() {
	 * //先取消之前重复绑定的事件，然后在绑定click事件--jQuery方法 console.log(data_id); $.ajax({
	 * type: "POST", url: fyToolUrl, data:
	 * {"action":"deleteTypeById","id":data_id}, cache: false, async : false,
	 * //异步 否则该ajax会异步执行 dataType: "json", success: function(result){
	 * if(result.result != null && result.result == true){ alert(result.msg);
	 * render(); }else{ alert(result.msg); console.log(result.msg); } },
	 * error:function (result){ console.log(result); alert("删除失败！"); } });
	 * $('.cover').fadeOut(); $('.delete-prompt').fadeOut(100); });
	 */
	// 点击取消
	$('#no').unbind().click(function() {
		$('.cover').fadeOut();
		$('.delete-prompt').fadeOut();
	});
	// 点击遮布
	$('.cover').unbind().click(function() {
		$('.cover').fadeOut();
		$('.delete-prompt').fadeOut();
	});
};

function handleOnDeleteYes(e) {
	$.ajax({
		type: "POST",
		url:fyToolUrl,
		cache: false, //禁用缓存
		async:true,
		data: {action:"deleteTypeById",id: e.data.id}, //传入组装的参数
		dataType: "json",
		success: function (result) {
			if(result.result != null){
				if(result.result == true){
					alert("删除[" + e.data.name + "]类型记录成功！");
					render();
				}else if(result.result == false && result.total > 0){
					console.log(result);
					alert("删除[" + e.data.name + "]类型记录失败。原因：该类型已有关联(设备或维修人员), 无法删除！");
				}else{
					console.log(result);
					alert("删除[" + e.data.name + "]类型记录失败，请刷新后重试！");
					render();
				}
			}else{
				console.log(result);
				alert("删除[" + e.data.name + "]类型记录失败，请刷新后重试！");
				render();
			}
		},
		complete:function(){
		
		},
		error: function(){//请求出错处理
			alert("删除[" + e.data.name + "]类型记录失败，请刷新后重试！");
			render();
		}
	});
    $('.cover').fadeOut();
    $('.delete-prompt').fadeOut();
}


// 点击添加
$('#add').click(function() {
	$('.cover').fadeIn();
	$('.catagory-add').fadeIn();
	// 点击关闭按钮
	$('#close-btn').unbind().click(function() {
		$('.catagory-add').fadeOut(100);
		$('.cover').fadeOut();
	});
	// 点击遮布
	$('.cover').unbind().click(function() {
		$('.catagory-add').fadeOut(100);
		$('.cover').fadeOut();
	});
})


// 点击提交
$('#add-form').submit(function() {
	var catagory = $('#catagory_name').val();
	$.ajax({
		type : "post",
		url : fyToolUrl,
		data : {
			"action" : "addType",
			"catagoryName" : catagory
		},
		cache : false,
		async : false,
		dataType : "json",
		context : document.body,
		success : function(result) {
			if (result.result != null && result.result == true) {
				alert(result.msg);
				render();
			} else {
				alert(result.msg);
				console.log(result.msg);
			}
		},
		error : function(result) {
			console.log(result);
			alert("添加失败");

		}
	});
	$('.cover').fadeOut();
	$('#catagory_name').val('');
	$('.catagory-add').fadeOut();
	return false;
});