package pioneer.pictures

import pioneer.resource

object PictureProgram extends App {
  // flip a picture horizontally, grayscale it, and rotate it left
  val image = Picture.loadImage(resource("/image.png"))
  val result = Picture.rotateLeft(Picture.grayScale(Picture.flipHorizontal(image)))
  Picture.saveImage(result, "output.png")

  // The final picture is now in the top folder of this project's directory,
  // in a file called "output.png".
}
