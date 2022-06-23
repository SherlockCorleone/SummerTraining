/**
 * 
 */
 $(function(){
 	var listUrl='/SummerTraining/shopadmin/getproductcategorylistbyshopid';
 	var insertUrl='/SummerTraining/shopadmin//insertproductcategorybatch';
 	var deleteUrl='/SummerTraining/shopadmin/deleteproductcategory';
 	getList();
 	
 	function getList(){
 		$.getJSON(listUrl,function(data){
 			if(data.success){
 				var dataList=data.productCategoryList;
 				var html='';
 				dataList.map(function(item,index){
 					html+='<div class="row row-product-category real"><div class="col-33">'
 	 					+item.productCategoryName+'</div><div class="col-33">'
 	 					+item.priority
 	 					+'</div><div class="col-33"><a href="#" class="button delete" data-id="'
 	 					+item.productCategoryId
 	 					+'">删除</a></div></div>';
 				});
 				$('.product-category-wrap').html(html);
 			}
 		});
 	}
 	$('.product-category-wrap').on('click', '.row-product-category.temp .delete',
			function(e) {
				console.log($(this).parent().parent());
				$(this).parent().parent().remove();

			});
 	$('.product-category-wrap').on('click', '.row-product-category.real .delete',
			function(e) {
				var target = e.currentTarget;
				$.confirm('你确定删除此条商品分类吗?\n对应的商品也会一并清空哦！', function() {
					$.ajax({
						url : deleteUrl,
						type : 'POST',
						data : {
							productCategoryId : target.dataset.id,
						},
						dataType : 'json',
						success : function(data) {
							if (data.success) {
								$.toast('删除成功！');
								getList();
							} else {
								$.toast('删除失败！');
							}
						}
					});
				});
			});
 	
 	$('#new').click(function(){
 		var html='<div class="row row-product-category temp">'
 		+ '<div class="col-33"><input class="category-input category" type="text" placeholder="类别名"></div>'
 		+'<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
 		+'<div class="col-33"><a href="#" class="button delete">删除</a></div></div>'
 		$('.product-category-wrap').append(html);
 	});
 	
 	$('#submit').click(function(){
 		var temp=$('.temp');
 		var productCategoryList=[];
 		temp.map(function(index,item){
 			var tempObj={};
 			tempObj.productCategoryName=$(item).find('.category').val();
 			tempObj.priority=$(item).find('.priority').val();
 			if(tempObj.productCategoryName&&tempObj.priority){
 				productCategoryList.push(tempObj);
 			}
 		});
 		$.ajax({
 			url:insertUrl,
 			type:'POST',
 			data:JSON.stringify(productCategoryList),
 			contentType:'application/json',
 			success:function(data){
 						if(data.success){
 							$.toast('提交成功！');
 						}else{
 							$.toast(data.errMsg);
 						}
 						getList();
 					}	
 		});

 	});
 });