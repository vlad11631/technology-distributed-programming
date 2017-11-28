package com.models;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "Message")
@XmlSeeAlso({User.class, UsersList.class, Task.class, TasksList.class})
public class Message implements Serializable {

    public static enum TypeMessage {
        OK,
        ERROR,
        LOAD_OBJECTS,
        CREATE,
        START_EDIT,
        STOP_EDIT,
        EDIT,
        DELETE,
        FINISH_SESSION
    }

    public static enum TypeObject {
        USER,
        TASK
    }

    private TypeMessage typeMessage;
    private TypeObject typeObject;
    private Object data;

    public Message() {
    }

    public Message(TypeMessage type, TypeObject target, Object data) {
        this.typeMessage = type;
        this.typeObject = target;
        this.data = data;
    }

    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
    }

    public TypeObject getTypeObject() {
        return typeObject;
    }

    public void setTypeObject(TypeObject typeObject) {
        this.typeObject = typeObject;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();

        //Записывем тип сообщения
        if (typeMessage != null) {
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
                case CREATE:
                    str.append("CREATE ");
                    break;
                case START_EDIT:
                    str.append("START_EDIT ");
                    break;
                case STOP_EDIT:
                    str.append("STOP_EDIT ");
                    break;
                case EDIT:
                    str.append("EDIT ");
                    break;
                case DELETE:
                    str.append("DELETE ");
                    break;
                case FINISH_SESSION:
                    str.append("FINISH_SESSION ");
                    break;
            }
        }

        //Записываем тип объекта
        if (typeObject != null) {
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
        if (data != null) {
            str.append(data.toString());
        }
        return str.toString();
    }
}
