/**
 * 
 */
 $(function(){
 	var productId=getQueryString("productId");
 	var infoUrl='/SummerTraining/shopadmin/getproductbyproductid?productId='+productId;
 	var productPostUrl='/SummerTraining/shopadmin/updateproduct';
 	getProduct();
 	
 	function getProduct(){
 		$.getJSON(infoUrl,function(data){
 			if(data.success){
 				var product=data.product;
 				$('#product-name').val(product.productName);
 				$('#product-desc').val(product.productDesc);
 				$('#priority').val(product.priority);
 				$('#normal-price').val(product.normalPrice);
 				$('#promotion-price').val(product.promotionPrice);
 				$('#create-date').val(product.createDate);
 				$('#last-modify-date').val(product.lastModifyDate);
 				$("#create-date").attr("disabled",false);
 				$('#last-modify-date').attr("disabled",false);
 				var productCategoryList=data.productCategoryList;
 				var html='';
 				productCategoryList.map(function(item,index){
 					html+='<option data-value="'+item.productCategoryId+'">'+item.productCategoryName+'</option>';
 				});
 				$('#product-category').html(html);
 				$("#product-category option[data-value='"+product.productCategory.productCategoryId+"']").attr("selected","selected");
 			}else {
						$.toast('获取商品信息失败:' + data.errMsg);
						$('#captcha_img').click();
					}
 		});
 	}
 	//详情图片的添加
 	$('.product-image-detail-div').on('change','.product-image-detail:last-child',function(){
 		if($('.product-image-detail').length<6){
 			$('#product-image-detail').append('<input type="file" class="product-image-detail">');
 		}
 	});
 	
 	$('#submit').click(
 		function(){
 			var product={};
 			product.productName=$('#product-name').val();
 			product.productDesc=$('#product-desc').val();
 			product.priority=$('#priority').val();
 			product.normalPrice=$('#normal-price').val();
 			product.promotionPrice=$('#promotion-price').val();
 			product.productCategory={
 				productCategoryId:$('#product-category').find('option').not(function(){
 					return !this.selected;	
 				}).data('value')
 			};
 			product.productId=productId;
 			//缩略图文件流
			var productImg=$('#product-image')[0].files[0]; 	
			var formData=new FormData();
			formData.append('productImg',productImg);
			//详情图文件流
			$('.product-image-detail').map(function(index,item){
				if($('.product-image-detail')[index].files.length>0){
					formData.append('productImgList'+index,$('.product-image-detail')[index].files[0]);
				}
			});		
			formData.append('productStr',JSON.stringify(product));
			var verifyCodeActual = $('#j_captcha').val();
			if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
			}
			formData.append("verifyCodeActual", verifyCodeActual);
			$.ajax({
				url: productPostUrl,
				type: 'POST',
				data: formData,
				contentType : false,
				processData : false ,
				cache: false,
				success: function(data) {
					if (data.success) {
						$.toast('提交成功');
						$('#captcha_img').click();
					} else {
						$.toast('提交失败:' + data.errMsg);
						$('#captcha_img').click();
					}
				
				}
			});
 		}
 	);
 	
 });