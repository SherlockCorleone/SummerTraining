/**
 * 
 */
 $(function(){
 	var getinfoUrl='/SummerTraining/frontend/getmainpageinfo';
 	
 	$.getJSON(getinfoUrl,function(data){
 		if(data.success){
 			var headLineList=data.headLineList;
 			var html='';
 			headLineList.map(function(item,index){
 				html+='<div class="swiper-slide img-wrap">'
 					+'<a href="'+item.lineLink+'"external><img class="banner-img"src="'+item.lineImageAddress+'"alt="'+item.lineName+'"></a>'
 					+'</div>';
 			});
 			$('.swiper-wrapper').html(html);
 			$('.swiper-container').swiper({
 				autoplay:5000,
 				autoplayDisableOnInteraction:true
 			});
 			
 			var shopCategoryList=data.shopCategoryList;
 			var shopCategoryListHtml='';
 			shopCategoryList.map(function(item,index){
 				shopCategoryListHtml+='<div class="col-50 shop-classify" data-category='+item.shopCategoryId+'>'
 									+'<div class="word"><p class="shop-title">'+item.shopCategoryName+'</p>'
 									+'<p class="shop-desc">'+item.shopCategoryDesc+'</p></div>'
 									+'<div calss="shop-classify-img-warp"><img class="shop-img" src="'+item.shopCategoryImageAddress+'"></div></div>'
 			});
 			$('.row').html(shopCategoryListHtml);
 		}
 	});
 	$('#me').click(function(){
 		$.openPanel('#panel-left-demo')
 	});
 	$('.row').on('click','.shop-classify',function(e){
 		var id=e.currentTarget.dataset.category;
 		window.location.href='/SummerTraining/frontend/shoplist?parentId='+id;
 	});
 });