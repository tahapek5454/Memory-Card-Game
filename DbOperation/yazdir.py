import mysql.connector


with open("evler.txt","r") as f:
     cnx = mysql.connector.connect(user="root", password="yazlab123", host="34.71.163.17", database="memorygame")
     print(cnx)
     myCursor = cnx.cursor()
     query = "INSERT INTO Home (name, score) VALUES (%s, %s)"
     data = []

     readed = f.read()

     deneme = readed.split('\n')


     print(deneme)

     for index in range(len(deneme)):

        if index%2==1:
            data.append(deneme[index-1])
            data.append(int(deneme[index]))
           

            data = tuple(data)

            try:
                myCursor.execute(query, data)
                cnx.commit()
            except Exception as e:
                print("Hata oldu")
                print(e)
            
            data = []


with open("kartlar.txt","r") as f:

    cnx = mysql.connector.connect(user="root", password="yazlab123", host="34.71.163.17", database="memorygame")
    print(cnx)
    myCursor = cnx.cursor() 
   

    query = "INSERT INTO Cards (name, home_id, score) VALUES (%s, %s, %s)"
    
    
    data = []

    readed = f.read()
    deneme = readed.split('\n')


    print(deneme)

    for index in range(len(deneme)):

        if index%2==1:
            data.append(deneme[index-1])
            data.append(3)
            data.append(int(deneme[index]))
            

            data = tuple(data)

            try:
                myCursor.execute(query, data)
                cnx.commit()
            except Exception as e:
                print("Hata oldu")
                print(e)
            
            data = []
        



    
