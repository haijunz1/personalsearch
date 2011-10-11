package com.w2g.operateObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class OperateObject {
	public void writeObjectIntoOutStream(Object object,OutputStream outputStream){
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Object readObjectFromInputStream(InputStream inputStream){
		Object object = null;
		try{
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			object = objectInputStream.readObject();
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}
}
