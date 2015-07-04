 <?xml version="1.0" encoding="utf-8"?>
  <mx:TitleWindow xmlns:mx="http://www.adobe.com/2006/mxml" showCloseButton="true" close="mx.managers.PopUpManager.removePopUp(this)" width="200" height="200" layout="absolute" creationComplete="setUrl()">

  <mx:Script>
   <![CDATA[
    import mx.controls.Alert;
    import mx.managers.*;
     
    [Bindable]
    public var url:String;
    public function setUrl():void {
     photo.source = url;
    }         
   ]]>
  </mx:Script>
   
  <mx:Image id="photo" width="100" height="88" source=""  verticalAlign="middle" horizontalAlign="center" />
 </mx:TitleWindow>





 import mx.managers.PopUpManager;
 public function onClickImg(e:Event):void
 {
   //mx.controls.Alert.show(e.target.parent.source);
   var vp:viewPhoto =
     viewPhoto(PopUpManager.createPopUp(this, viewPhoto, false));
   vp.url = e.target.parent.source;
   PopUpManager.centerPopUp(vp);
 }


import mx.collections.ArrayCollection;
 import com.adobe.webapis.flickr.*;
 import com.adobe.webapis.flickr.events.*;
 //import org.osflash.thunderbolt.Logger;
  
 private var API_KEY:String = 'ここにFlickrのAPI KEYを記述';
  
 private var max_result:Number;
 private var flickr_service:FlickrService;
  
 [Bindable] public var photos:ArrayCollection;
  
 private function init():void
 {
   max_result = Number(combo_limit.value.toString());
   flickr_service = new FlickrService(API_KEY);
   flickr_service.addEventListener(FlickrResultEvent.PHOTOS_GET_RECENT, onPhotosResult);  
   flickr_service.addEventListener(FlickrResultEvent.PHOTOS_SEARCH, onPhotosResult);
   
   btn_1.addEventListener(MouseEvent.CLICK, onClickBtn1);  
   btn_2.addEventListener(MouseEvent.CLICK, onClickBtn2);    
 }
  
 private function onClickBtn1(e:MouseEvent):void
 {
   max_result = Number(combo_limit.value.toString());
   flickr_service.photos.getRecent('', max_result, 1);
 }
  
 private function onClickBtn2(e:MouseEvent):void
 {
   max_result = Number(combo_limit.value.toString());
   flickr_service.photos.search('', 'flower', 'any', '', null, null, null, null, -1, '', max_result, 1, 'date-posted-desc');
 }
  
 private function onPhotosResult(e:FlickrResultEvent):void
 {
   //Logger.debug('debug', '結果は？');
   if (e.success) {
     var photo_list:PagedPhotoList
     photo_list = e.data.photos;
     photos = new ArrayCollection(photo_list.photos);
     //Logger.debug('debug', '取得できました');
   } else {
     //Logger.error('error', '取得できませんでした');
   }
 }