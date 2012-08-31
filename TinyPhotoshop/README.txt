----------------------
TinyPho
----------------------

	1.Manual:
		
		(1) (OPTIONAL) Edit the "kernels.xml" file to edit kernels freely.
		(2) double press the jar.
		(3) select an image by clicking on the textbar.
		(4) select an effect by clicking on the text on the right hand side of the menubar.
		
		
		-->Options:
		
			(a) edit the asked information as you please.
				-->in the case of Projective Transformations there is an option to add, delete or select a previously stored
					kernel.
					
					-->Warning:
							The program will store it's database of Projective Transformation at the moment you close the window in the
							"kernels.xml" file. I have supplied a backup version of that file.
					
			
			(b) press the "Apply button" to put the effect on the image.
			(c) press the "Save button" to save the newly created image on your system.
			(d) press the "Reset button" to return the image to it's original state.
	
	
	2.Extras implemented:
	
		-Sharpen:
			(1) Fast Gaussian blurring:
				-->the class "GaussalFast" has it's own Convolution object and  a "createFastGaussalKernel" -method with both a horizontal and a vertical option. When the "applyTo"-method is called on an "ImageProcessor" the class will first apply the convolution with the horizontal kernel on the ImageProcessor and, after setting the convolution's kernel to the vertical kernel,
				apply the convolution again on the same ImageProcessor.
			
		-Mosaic:
			(1) No square root used for determining the closest point.
				-->square root did not contribute to the relative distance between the random points and the selected pixel.
			
			(2) No implementation of a faster algorythme to find the nearest random point.
			
		-Sphere:
			(1)  Softer lens:
				--> implemented with the use of the provided algorythme.
			
			(2) Userinterface gives the option to chance radius, rho and the centerpoint
			but not with the use of sliders.
			
		-Projective Transformations:
			I have implemented this part of the assignment in a different way. I give the option to create your own kernels or delete them in addition to some standard kernels. Since the program will apply the effect to the lastest created or loaded image you can
			easely add different transformations ontop of eachother by multiple (" select kernel"->"apply") sequences and restore the image to it's original state with the reset button. Because the input is more open an invertion of the previous transformation has to be created by edditing the kernel with the inverse input.
			
		