from flask import Flask, jsonify, request,Response
import mysql.connector
import redis
from apscheduler.schedulers.background import BackgroundScheduler
from flask import abort

# API  
app = Flask(__name__)
global conn 
conn = mysql.connector.connect(host='10.64.15.42',
                        user='root',
                        password='yazlab123',
                        database='memorygame'
                        )
global conn_cursor
conn_cursor = conn.cursor(buffered=True)


scheduler = BackgroundScheduler()
@scheduler.scheduled_job('cron', hour='*')

def refresh_database():
    conn_cursor.close()
    conn.close()
    conn = mysql.connector.connect(host='10.64.15.42',
                        user='root',
                        password='yazlab123',
                        database='memorygame'
                        )
    conn_cursor = conn.cursor(buffered=True)

def getCardsFromDb():
    
    response = []

    query = "SELECT c.id, c.name, c.score, h.Name, h.Score, c.image FROM Cards as c, Home as h WHERE c.home_id = h.id"
    try:
        conn_cursor.execute(query)

        result = conn_cursor.fetchall()
        
        for i in result:
            myDict = {}
            myDict["cardId"]=i[0]
            myDict["cardName"]=i[1]
            myDict["cardScore"]=i[2]
            myDict["homeName"]=i[3]
            myDict["homeScore"]=i[4]
            
            
            response.append(myDict)

    except Exception as e:
        print("hata")
    
    return response

    

@app.route('/health', methods=['GET'])
def health():
        query = "SELECT c.id, c.name, c.score, h.Name, h.Score, c.image FROM Cards as c, Home as h WHERE c.home_id = h.id"
        conn_cursor.execute(query)
        result = conn_cursor.fetchall()
        
        if len(result)>0:
            return Response(
            "Container is healthy.",
                status=200,
            )
        
        return Response(
            "Container is unhealthy.",
                status=500,
            )   

@app.route("/getCards", methods = ["GET"])
def getCard():
    if request.method == 'GET':     
        return jsonify(getCardsFromDb())
    return "Hata" 
    
    
if __name__ == '__main__':
    scheduler.start()
    app.run("0.0.0.0",debug=True,port=5001)
