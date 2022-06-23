/**
 * 
 */
$(function() {
	var initUrl = '/SummerTraining/shopadmin/getshopinitinfo';
	var registerShopUrl = '/SummerTraining/shopadmin/registershop';
	getShopInitInfo();
	function getShopInitInfo() {
		$.getJSON(initUrl, function(data) {
			if (data.success) {
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
				$('#area').html(tempAreaHtml);
			}else {
				$.toast('获取区域信息和店铺分类失败:' + data.errMsg);
				$('#captcha_img').click();
			}
		});
		$('#submit').click(function() {
			var shop = {};
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
				url: '/SummerTraining/shopadmin/registershop',
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
