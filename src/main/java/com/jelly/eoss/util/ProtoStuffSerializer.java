package com.jelly.eoss.util;

import com.google.common.primitives.Ints;
import io.protostuff.GraphIOUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.ConcurrentHashMap;

public class ProtoStuffSerializer {
    private static final Logger log = LoggerFactory.getLogger(ProtoStuffSerializer.class);
    private final static ConcurrentHashMap<Class, Schema> cachedSchema = new ConcurrentHashMap<>();

    private  static Schema getSchema(Class clazz) {
        Schema schema = cachedSchema.get(clazz);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(clazz);
            cachedSchema.put(clazz, schema);
        }
        return schema;
    }

    private static <T> byte[] serialize( final T source ) {
        final Class<T> clazz = (Class<T>) source.getClass();
        final LinkedBuffer buffer = LinkedBuffer.allocate();
        final byte[] protostuff;
        try {
            final Schema<T> schema = getSchema( clazz );
            protostuff = ProtostuffIOUtil.toByteArray(source, schema, buffer);
            return protostuff;
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        finally {
            buffer.clear();
        }
        return null;
    }

    private static <T> T deserialize( final byte[] bytes , final Class clazz ) {
        try {
            Schema schema = getSchema(clazz);
            final T result = (T)schema.newMessage();
            ProtostuffIOUtil.mergeFrom(bytes, result, schema);
            return result;
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> byte[] writeObject (final T source) {
        final Class cls = source.getClass();
        final Schema schema = getSchema(cls);

        final LinkedBuffer buffer = LinkedBuffer.allocate();
        try {
            ByteArrayOutputStream temp = new ByteArrayOutputStream();
            String cName = cls.getName();
//            System.out.println("read, cname=" + cName);
            byte [] cname = cName.getBytes("UTF-8");
//            System.out.println("write, cname length=" + cname.length);
//            System.out.println("\nwrite, cname bytes=================\n");
//            for(byte b : cname){
//                System.out.print(b + ",");
//            }
//            System.out.println("\nwrite, cname bytes=================\n");
            byte[] cnameLengthBytes = Ints.toByteArray(cname.length);
            temp.write(cnameLengthBytes);
            temp.write(cname);
            GraphIOUtil.writeTo(temp, source, schema, buffer);
            byte[] bytes = temp.toByteArray();
//            System.out.println("write, length=" + bytes.length);
//            System.out.println("\nwrite, bytes=================\n");
//            for(byte b : bytes){
//                System.out.print(b + ",");
//            }
//            System.out.println("\nwrite, bytes=================\n");
            temp.close();
            return bytes;
        }
        catch ( final Exception e ) {
            if(log.isDebugEnabled()){
                log.debug("proto stuff write object error", e);
            }
        }
        finally {
            buffer.clear();
        }

        return null;
    }

    public static <T> T readObject(byte[] buff) {
        Class clazz = null;
        try {
//            System.out.println("\nread, bytes=================\n");
//            for(byte b : buff){
//                System.out.print(b + ",");
//            }
//            System.out.println("\nread, bytes=================\n");
//            System.out.println("read, length=" + buff.length);
            ByteArrayInputStream bis = new ByteArrayInputStream(buff);
            byte[] cnameLengthBytes = new byte[4];
            bis.read(cnameLengthBytes);
            int cnameLength = Ints.fromByteArray(cnameLengthBytes);
//            System.out.println("read, cname length=" + cnameLength);

            byte[] cnameBytes = new byte[cnameLength];
            bis.read(cnameBytes);
//            System.out.println("\nread, cname bytes=================\n");
//            for(byte b : cnameBytes){
//                System.out.print(b + ",");
//            }
//            System.out.println("\nread, cname bytes=================\n");
            String cName = new String(cnameBytes, "UTF-8");
//            System.out.println("read, cname=" + cName);
            clazz = Class.forName(cName);
            Schema schema = getSchema(clazz);
            T result = (T)schema.newMessage();
            GraphIOUtil.mergeFrom(bis, result, schema);
            bis.close();
            return result;
        }
        catch ( final Exception e ) {
            if(log.isDebugEnabled()){
                log.debug("proto stuff read object error", e);
            }
        }

        return null;
    }
}
