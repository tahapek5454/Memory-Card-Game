import base64
import glob
import mysql.connector



imagesPath = glob.glob(r"C:\Users\ASUS Pc\Desktop\VSCodeProject\yazlab13\HarryPotterImages\total\*")
base64List = []
indexler = []

for i in imagesPath:
    new = i[72::]
    new2 = new.split('.')
   
    indexler.append(new2[0])

#image to base64

for i in range(len(imagesPath)):
  
    with open(imagesPath[i], "rb") as image_file:
        encoded_string = base64.b64encode(image_file.read())
        base64List.append(encoded_string)

cnx = mysql.connector.connect(user="root", password="yazlab123", host="34.71.163.17", database="memorygame")
myCursor = cnx.cursor()

for i in range(len(base64List)):
    print(i)
    query = "UPDATE memorygame.Cards SET image=%s WHERE id=%s"
    data = []
    data.append(base64List[i])
    data.append(indexler[i])
    data = tuple(data)
    myCursor.execute(query, data)
    cnx.commit()
   




# base64 to image
cnx = mysql.connector.connect(user="root", password="yazlab123", host="34.71.163.17", database="memorygame")
myCursor = cnx.cursor()
query = "SELECT  image, name FROM memorygame.Cards WHERE id=41"
myCursor.execute(query)
data = myCursor.fetchall()

print(data[0][1])

with open("ceviri2.jpg","wb") as image:
    image.write(base64.b64decode(data[0][0]))


