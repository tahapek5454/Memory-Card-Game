import flask
import mysql.connector
from flask import request, jsonify


app = flask.Flask(__name__)

username_password=[]


@app.route('/', methods=['GET'])
def home():
    return '''Login api is running now.'''

def login_control(username,password):
    conn = mysql.connector.connect(host='34.71.163.17',
                        user='root',
                        password='yazlab123',
                        database='memorygame'
                        )
    
    conn_cursor = conn.cursor(buffered=True)

    conn_cursor.execute("Select Email from Users where Username='{}' AND Password='{}'".format(username,password))
    result=conn_cursor.fetchall()

    if len(result)==0:
        conn_cursor.close()
        return False
    conn_cursor.close()
    return True
      
def register_function(email,username,password):
    conn = mysql.connector.connect(host='34.71.163.17',
                        user='root',
                        password='yazlab123',
                        database='memorygame'
                        )
    
    conn_cursor = conn.cursor(buffered=True)

    conn_cursor.execute("Select Password from Users where Username='{}' OR Email='{}'".format(username,email))
    result=conn_cursor.fetchall()
    print(result)
    if(len(result)>0):
        conn_cursor.close()
        return False
    
    else:
        
        conn_cursor.execute("INSERT INTO `memorygame`.`Users` (`Username`, `Email`,`Password`) VALUES ('{}', '{}','{}');".format(username,email,password))
        conn.commit()
        conn_cursor.close()
        return True
              
def change_password(username,old_password,new_password):
    conn = mysql.connector.connect(host='34.71.163.17',
                        user='root',
                        password='yazlab123',
                        database='memorygame'
                        )
    
    conn_cursor = conn.cursor(buffered=True)
    if login_control(username=username,password=old_password):
        
        conn_cursor.execute("UPDATE `memorygame`.`Users` SET `Password`='{}' WHERE  `Username`='{}';".format(new_password,username))
        conn.commit()
        conn_cursor.close()
        return True
    else:
        conn_cursor.close()
        return False
       
    
@app.route('/api/register/<data>', methods=['GET','POST'])
def register(data):
        username=data.split('-')[0]
        email=data.split('-')[1]
        password=data.split('-')[2]
        
        if register_function(email=email,username=username,password=password):
            return "OK"
        
        return "NOTOK DUPUSR"    
    
    
# A route to return all of the available entries in our catalog.
@app.route('/api/login/<data>', methods=['GET','POST'])
def login(data):
        username=data.split('-')[0]
        password=data.split('-')[1]
        if login_control(username=username,password=password):
            return "OK"
        
        return "NOTOK FLSUSR"

@app.route('/api/chgpassword/<data>', methods=['GET','POST'])
def chgpassword(data):
        username=data.split('-')[0]
        old_password=data.split('-')[1]
        new_password=data.split('-')[2]
        if change_password(username=username,old_password=old_password,new_password=new_password):
            return "OK"
        
        return "NOTOK FLSUSR"    


app.run("0.0.0.0",debug=True,port=5000)


