package com.models;

import java.io.Serializable;

public class Message implements Serializable{
    public static enum TypeMessage {
        OK,
        ERROR, 
        LOAD_OBJECTS, 
        LOAD_OBJECT,       
        CREATE_OBJECT,        
        START_EDIT_OBJECT,
        STOP_EDIT_OBJECT,
        DELETE_OBJECT,
        FINISH_SESSION
    }
    
    public static enum TypeObject {
        USER,
        TASK
    }
    
    private final TypeMessage typeMessage;
    private final TypeObject typeObject;
    private final Object data;
    
    public Message(TypeMessage type, TypeObject target, Object data) {
        this.typeMessage = type;
        this.typeObject = target;
        this.data = data;
    }
    
    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public TypeObject getTypeObject() {
        return typeObject;
    }

    public Object getData() {
        return data;
    }
    
    public String toString(){
        StringBuffer str = new StringBuffer();
        
        //Записывем тип сообщения
        if(typeMessage != null){
            switch (typeMessage) {
                case OK:
                    str.append("OK ");
                    break;
                case ERROR:
                    str.append("ERROR ");
                    break;
                case LOAD_OBJECTS:
                    str.append("LOAD_OBJECTS ");
                    break;
                case LOAD_OBJECT:
                    str.append("LOAD_OBJECT ");
                    break;
                case CREATE_OBJECT:
                    str.append("CREATE_OBJECT ");
                    break;
                case START_EDIT_OBJECT:
                    str.append("START_EDIT_OBJECT ");
                    break;
                case STOP_EDIT_OBJECT:
                    str.append("STOP_EDIT_OBJECT ");
                    break;
                case DELETE_OBJECT:
                    str.append("DELETE_OBJECT ");
                    break;
                case FINISH_SESSION:
                    str.append("FINISH_SESSION ");
                    break;
            }
        }
        
        //Записываем тип объекта
        if (typeObject != null){
            switch (typeObject) {
                case USER:
                    str.append("USER ");
                    break;
                case TASK:
                    str.append("TASK ");
                    break;
            }
        }
        
        //Записываем объект
        if(data != null){
            str.append(data.toString());
        }
    return str.toString();        
    }
}
