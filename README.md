# ECE4800J Project -  Rainbow (omega ver.)

## Project Introduction

&nbsp; &nbsp; This project mainly creates an Android application named **Rainbow** that can recognize colors from an image and display their names. Rainbow aims to help people with color vision deficiency to recognize colors at any time and anywhere quickly and conveniently.
 
&nbsp; &nbsp; In omega version, users can do the following things through *Rainbow*:

&nbsp; &nbsp; - Upload image from local storage or directly take a photo. 

&nbsp; &nbsp; - Switch between two color detection modes: pixel detection mode and blob detection mode. Users can choose to recognize the color in the image in the place where he/she touched, and get its RGB value and the name of this color; or to choose specific color on the image and receive the outline of the color blob and the blob's color.

&nbsp; &nbsp; - Switch between two color libraries: Complex library (default) and Simple library. The complex library provides 900+ kinds of colors and provides detailed difference among colors; while the simple library contains 21 colors and provides a general view of the color. If the user has difficulty in understanding the words used in complex color library, or doesn't need such detailed view of the color, he/she can switch to simple library and begin detection.

&nbsp; &nbsp; - Detailed color information & color harmony: After selecting a color in the color detection page, users can press "detail" button to enter the detail page.  

&nbsp; &nbsp; - Musical aid: User can learn about the mood of certain color, and listen to pieces of music that reperesents different colors.

&nbsp; &nbsp; - Interface rotation: For images with large width and small height, user can open the auto-rotation function of the mobile phone, rotate the device and get a more comfortable view in the horizontal interface. 

&nbsp; &nbsp; - User guide: User can check the guide of using *Rainbow* in the "user guide" page if he/she doesn't know how to use this application.

&nbsp; &nbsp; - Feedback: Users can contact developers through the contact information in the feedback page if they have any suggestions on *Rainbow*.

## Dependencies

&nbsp; &nbsp; This project is developed using Java in *Android Studio 2021.2.1.15*, and has dependency on *OpenCV 4.6.0*. To test the project, you need to first configure OpenCV library to Android Studio; For using the application, users don't need to download extra packages since OpenCV library is included in the *.apk* file.

## Directory Structure
├── README.md

├── AndroidManifest.xml 

│   └── res             

│   &nbsp;&nbsp;&nbsp;└── drawable &nbsp;&nbsp;&nbsp;&nbsp; <font color = grey>// image resource</font>

│   &nbsp;&nbsp;&nbsp;└── layout &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <font color = grey>// interface setting files (vertical, default interface)</font>

│   &nbsp;&nbsp;&nbsp;└── layout-land &nbsp; <font color = grey>// interface setting files (horizontal) </font>

│   &nbsp;&nbsp;&nbsp;└── raw  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <font color = grey>// music resources </font>

│   &nbsp;&nbsp;&nbsp;└── values 

│   &nbsp;&nbsp;&nbsp;└── xml

│   └── assets&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <font color = grey>// font resources</font>

│   └── java/com/example/testproject  &nbsp;&nbsp;&nbsp;&nbsp; <font color = grey>// back-end source code</font>


## Instructions for use
&nbsp; &nbsp; If you only want to use this app and is uninterested in its code, you can download the apk file from the release named *Rainbow-omega* and install it on your Android device. Both virtual device and physical device are available.

&nbsp; &nbsp; If you want to check the source code and run this application in test mode, you can create an empty project in Android Studio and paste the resources into ProjectName/app/src/main. Also, OpenCV library is needed for running. Configuration files are not included in the respitory, since Android Studio and OpenCV configuration will be quite different due to developer's own settings. 

&nbsp; &nbsp; To use *Rainbow*, users can open the application and enter the main page. In the main page, there're two large buttons: choose image from local storage, or take photo from camera. Users can choose one way to upload an image. If the user doesn't know how to use the application, he/she can press the button on the top left corner and read user's guide.

&nbsp; &nbsp; After uploading image, the application will jump to the color detection page. Users can choose detection mode by pressing the mode switch button on the top of the page. In color pixel detection mode (default), users can touch any place they want to examine on the image, and get the recognition result (color name and RGB value) shown in the texts below. In color blob detection mode, users can touch any place to specify the color they want to examine; the result in text form will shown below just like in pixel detection mode, and the outline of color blobs will also be shown on the image. By switching between complex and simple color library using the library button, user can either get a detailed classification or a general view of the color.

&nbsp; &nbsp; If the uploaded image has large width and small height, and user find it shown too small in the vertical interface, he/she can open the auto-rotation function of the mobile phones, rotate the device, and the color detection page will also automatically rotate. By doing this, user can have a clearer view of the image.

&nbsp; &nbsp; For more information of the selected color, user can press "detail" button on the detection page and enter detail page. On the top of the page, user can see the location of this color on the color wheel and its RGB/HSL/Hex color code. Scrolling down the page, user can see color matching schemes that fits basic color harmony theories.

&nbsp; &nbsp; To get an audio sense of colors, user can press the note button on the main page and enter "musical aid" page. After selecting a color, user can read the text below introducing the mood this color represents, and listen to small pieces of melody expressing the mood of this color.

&nbsp; &nbsp; In all pages, users can click the information icon in the top-right corner and enter the feedback page. They can read the introduction about the development team and offer feedbacks to developers through the contact information on this page.

## Update Log
&nbsp; &nbsp; Omega version (2022/07/28) :

- cursor on the pixel user pressed

- color blobs outline width dynamic change

- simple / complex color library

- detail page

- musical aid

- user guide page

&nbsp; &nbsp; Beta version (2022/07/07) :

- upload images (local storage / camera photo)

- detect color blobs and display outline

- outline color dynamic change

- detection page rotation with gravity

- feedback page

&nbsp; &nbsp; Alpha version (2022/06/16) :

- detect color pixel

- display color RGB