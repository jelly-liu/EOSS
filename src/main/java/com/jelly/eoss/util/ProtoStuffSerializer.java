package com.jelly.eoss.util;

import com.google.common.primitives.Ints;
import io.protostuff.GraphIOUtil;
import io.protostuff.LinkedBuffer;
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

    public static <T> byte[] serialize( final T source ) {
        final Class<T> clazz = (Class<T>) source.getClass();
        final LinkedBuffer buffer = LinkedBuffer.allocate();
        try {
            final Schema<T> schema = getSchema( clazz );
            ByteArrayOutputStream temp = new ByteArrayOutputStream();
            GraphIOUtil.writeTo(temp, source, schema, buffer);
            return temp.toByteArray();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        finally {
            buffer.clear();
        }
        return null;
    }

    public static <T> T deserialize( final byte[] bytes , final Class clazz ) {
        try {
            Schema schema = getSchema(clazz);
            final T result = (T)schema.newMessage();
            GraphIOUtil.mergeFrom(bytes, result, schema);
            return result;
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> byte[] writeObject (final T source) {
        final Class cls = source.getClass();
        final LinkedBuffer buffer = LinkedBuffer.allocate();
        try {
            final Schema schema = getSchema(cls);
            ByteArrayOutputStream temp = new ByteArrayOutputStream();
            byte [] cname = cls.getName().getBytes();
            byte[] cnameLengthBytes = Ints.toByteArray(cname.length);
            temp.write(cnameLengthBytes);
            temp.write(cname);
            GraphIOUtil.writeTo(temp, source, schema, buffer);
            temp.close();
            return temp.toByteArray();
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

    public static Object readObject(byte[] buff) {
        Class clazz = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(buff);
            byte[] cnameLengthBytes = new byte[4];
            bis.read(cnameLengthBytes);
            int cnameLength = Ints.fromByteArray(cnameLengthBytes);

            byte[] cnameBytes = new byte[cnameLength];
            bis.read(cnameBytes);
            clazz = Class.forName(String.valueOf(cnameBytes));
            Schema schema = getSchema(clazz);
            final Object result = schema.newMessage();
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
