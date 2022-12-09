import flask
import mysql.connector
import redis
from flask import request, jsonify


app = flask.Flask(__name__)
r = redis.Redis(
    host='34.170.163.125',
    port=6379, 
    password='kouyazlab',
)

r.flushdb()
r.flushall()
username_password=[]
conn = mysql.connector.connect(host='34.71.163.17',
                        user='root',
                        password='yazlab123',
                        database='memorygame'
                        )
    
conn_cursor = conn.cursor(buffered=True)



@app.route('/', methods=['GET'])
def home():
    return '''Login api is running now.'''

def login_control(username,password):
    
    # r.set('mykey', 'Hello from Python!')
    # value = r.get('mykey')
    # value = value.decode("utf-8")
    # print(type(value))
    
    query="Select Email from Users where Username='{}' AND Password='{}'".format(username,password)

    if(r.exists(query)):
        print("Login Cache")
        if r.get(query).decode('utf-8')=="True":
            return True
        else:
            return False
    
    else:
        conn_cursor.execute(query)
        result=conn_cursor.fetchall()
        print(result)
        if len(result)==0:
            r.set(query,"False")
            return False
        r.set(query,"True")
        return True
      
def register_function(email,username,password):

    query="Select Password from Users where Username='{}' OR Email='{}'".format(username,email)

    if(r.exists(query)):
        print("Register Cache")
        if r.get(query).decode('utf-8')=="True":
            return True
        else:
            return False
    
    
    else:
        conn_cursor.execute("Select Password from Users where Username='{}' OR Email='{}'".format(username,email))
        result=conn_cursor.fetchall()
        
        if(len(result)>0):

            r.set(query,"False")
            return False
    
        else:
            conn_cursor.execute("INSERT INTO `memorygame`.`Users` (`Username`, `Email`,`Password`) VALUES ('{}', '{}','{}');".format(username,email,password))
            conn.commit()

            r.set(query,"True")
            return True
              
def change_password(username,old_password,new_password):
    
    query="UPDATE `memorygame`.`Users` SET `Password`='{}' WHERE  `Username`='{}';".format(new_password,username)
    
    if login_control(username=username,password=old_password):
        r.flushdb()
        r.flushall()
        
        
        if(r.exists(query)):
            
            print("Change password Cache")
            if r.get(query).decode('utf-8')=="True":
                return True
            else:
                return False
    
        
        else:
            conn_cursor.execute(query)
            conn.commit()
            r.set(query,"True")
            return True
    else:
        r.set(query,"False")
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


