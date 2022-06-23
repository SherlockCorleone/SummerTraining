/**
 * 
 */
 $(function() {
 	var shopId=getQueryString('shopId');
	var shopInfoUrl='/SummerTraining/shopadmin/getshopbyid?shopId='+shopId;
	var updateShopUrl='/SummerTraining/shopadmin/updateshop';
	
	getShopInfo();
	function getShopInfo() {
		$.getJSON(shopInfoUrl, function(data) {
			if (data.success) {
			var shop=data.shop;
			$('#shop-name').val(shop.shopName);
			$('#shop-phone').val(shop.shopPhone);
			$('#shop-address').val(shop.shopAddress);
			$('#shop-desc').val(shop.shopDesc);
				var tempHtml = '';
				var tempAreaHtml = '';
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId + '">'
						+ item.shopCategoryName + "</option>";
				});
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(tempHtml);
				$("#shop-category option[data-id='"+shop.shopCategory.shopCategoryId+"']").attr("selected","selected");
				$('#area').html(tempAreaHtml);
				$("#area option[data-id='"+shop.area.areaId+"']").attr("selected","selected");
			}else {
				$.toast('获取商铺信息失败:' + data.errMsg);
				$('#captcha_img').click();
			}
		});
		$('#back').attr('href','/SummerTraining/shopadmin/shopmanagement?shopId='+shopId);
		$('#submit').click(function() {
			var shop = {};
			shop.shopId=shopId;
		shop.shopName = $('#shop-name').val();
		shop.shopAddress = $('#shop-address').val();
		shop.shopPhone = $('#shop-phone').val();
		shop.shopDesc = $('#shop-desc').val();
		shop.shopCategory = {
			shopCategoryId : $('#shop-category').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		shop.area = {
			areaId : $('#area').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		var shopImg = $("#shop-image")[0].files[0];
		var formData = new FormData();
		formData.append('shopImg', shopImg);
		formData.append('shopStr', JSON.stringify(shop));
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
			$.ajax({
				url: updateShopUrl,
				type: 'POST',
				data: formData,
				contentType : false,
				processData : false ,
				cache: false,
				success: function(data) {
					if (data.success) {
						$.toast('提交成功');
					} else {
						$.toast('提交失败:' + data.errMsg);
					}
					$('#captcha_img').click();
				}
			});
		});
	}
})
 