The goal of our redesigned Picture library was to make it easer to compose
operations without having to load and save an image each time. We accomplished
that goal, now it's time for critique. In this round of critique we're focusing
on the following question:

Is there anything that is still annoying or difficult about the operations we
have in the library (i.e., flipping, rotating, and grayscaling)?

## Better names
There are a few things relating to names that would make the user's experience
better.

### Stop referencing the `Picture` object so much

It's annoying to always write `Picture.` before each operation. We could fix it
in our program by importing all the functions from the `Picture` object:
  ```scala 
  import Picture._
  ```
That's a little bit of extra typing on the user's part, but the user saves every time they call an operation.
  
### Inconsistent names in the `Picture` object

The `loadImage` and `saveImage` functions are inconsistently named. The names of
all the other operations in the library contain only the verb (e.g.,
`flipHorizontal`) and not the noun `Image`. There are a couple of ways to change
it.

#### Let the user handle it

As the user, I can change the name when I import, like so:
```scala
import Picture.{loadImage => load}
```
But it's really the _implementor's_ responsibility to provide good names, so...

#### Change the `Picture` object       
A better way to address this issue is to rename the functions in the `Picture`
object, so they're consistent:
```scala
def load(filename: String): BufferedImage = {
  ...
}

def load(inputStream: InputStream): BufferedImage = {
  ...
}

def save(image: BufferedImage, 
         filename: String, 
         format: String = "png"): Boolean = {
  ...
}
```

### `BufferedImage` is a weird name
This issue is a bit more subtle. 

The result type for each of the operations is `BufferedImage`, which is a type
defined in another library. The user of _our_ library doesn't need to know that
the image is buffered (whatever that means), so it would be nicer if the result
type had a better name than `BufferedImage`. 

This issue didn't cause a problem for us in the simple program we wrote. But
what if the user wanted to write their own function that performed several
operations. For example, what if the user wanted to take the three operations
from our sample program and turn them into their own function? Then, the user
would have to declare the parameter and result types of the function to be
`BufferedImage`:
```scala
package pioneer.pictures

import java.awt.image.BufferedImage
import pioneer.resource

object PictureProgram extends App {
  /** flip a picture horizontally, grayscale it, and rotate it left */
  def myOperation(image: BufferedImage): BufferedImage = {
    Picture.rotateLeft(Picture.grayScale(Picture.flipHorizontal(image)))
  }

  val image = Picture.loadImage(resource("/image.png"))
  val result = myOperation(image)
  Picture.saveImage(result, "output.png")
}
```
Notice that the user has to import the `BufferedImage` type themselves, to be
able to use that type in the `myOperation` function. In this scenario,
it would be nice if:
   1. The user didn't have to import `BufferedImage`.
   1. The user could work with a type that has a better name. Maybe just
      `Image`?
