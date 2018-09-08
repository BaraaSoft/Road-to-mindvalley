# Road-to-mindvalley
This Android App developed by Using MVP Clean Architecture.
The App also supports endless scrolling for a long list and pull to refresh.
Furthermore, this project also contains my library (ValleyDownloader Library) to download images or files and cache them a certain amount of time to improve the performance.

For more details about why I choose MVP clean architecture  over other design architecture, feel free to check out my [blog artical about MVP](https://baraabytes.com/why-you-should-use-mvp-clean-architecture-in-your-next-android-project/ "blog artical about MVP")

## ValleyDownloader Library
It is For downloading image and file. it developed to be easily integrated with RxJava as well as Dagger if you are using it as your dependency injection.

##### The library's Built-in Dagger Module for getting  and parsing JSON file from the endpoint  http://pastebin.com/raw/wgkJgazE
`MediaApiModule.class`
##### The library's Built-in Dagger Module for getting any Image/file from any given url
`FileApiModule.class`

##### Usage with Dagger 
```java
component = DaggerApplicationComponent.builder()
.applicationModule(new ApplicationModule(this))
.mediaApiModule(new MediaApiModule())
.fileApiModule(new FileApiModule())
.build();
```


##### `ValleyDownloader` Is the core class for downloading and it implements  
##### `IDownloadService` interface.  

```java

/************** IDownloadService interface ****************/

/** Get  json data from network **/
Observable<BaseResponse> getMediaFromNetwork(String baseJsonUrl);
/** Get cached json data in the memory **/
Observable<BaseResponse> getCachedMedia();
/** 
* Get cached json data within 20sec from last fetch,
* if last fetch happen more than 20sec ago fetch new data from a network **/
Observable<BaseResponse> getMedia(String baseJsonUrl); 


/** Get images from network **/
Observable<Bitmap> getImagesFromNetwork(String baseJsonUrl);
/** Get a cached images in the memory **/
Observable<Bitmap> getCachedImages();
/** Get cached images within 20sec from last fetch,
* if last fetch happen more than 20sec ago fetch new data from a network **/
Observable<Bitmap> getImages(String baseJsonUrl);



/** Get files(example *.pdf , *.txt) from a network.
* if we say it has same Url path as small image url in base Json response. **/
Observable<byte[]> getFilesFromNetwork(String baseJsonUrl);
/** Get cached files in the memory **/
Observable<byte[]> getCachedFiles();
/** Get a cached files within 20sec from last fetch,
* if last fetch happen more than 20sec ago fetch new data from a network **/
Observable<byte[]> getFiles(String baseJsonUrl);



/** get a single file from given url **/
Observable<byte[]> getFileFromNetwork(String fileUrl);
/** get a cached file in memory **/
Observable<byte[]> getCachedFile();
/** Get a cached file within 20sec from last fetch,
* if last fetch happen more than 20sec ago fetch a new data from a network **/
Observable<byte[]> getFile(String fileUrl);

/** Get a single image from network **/
Observable<Bitmap> getImageFromNetwork(String imageUrl);
/** Get a single cached image in the memory **/
Observable<Bitmap> getCachedImage();
/** Get a single cached image within 20sec from last fetch,
* if last fetch happen more than 20sec ago fetch new data from a network **/
Observable<Bitmap> getImage(String imageUrl);
```





##### if you are using Dagger You could just inject ValleyDownloadService dependencies as below

```java
@Provides
public IDownloadService providesValleyDownloadServices(IMediaJsonApiServices mediaJsonApiServices, IFileApiServices fileApiServices){
return new ValleyDownloadService(fileApiServices,mediaJsonApiServices);
}
```




















