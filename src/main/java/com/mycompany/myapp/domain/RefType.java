package com.mycompany.myapp.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mycompany.myapp.json.ObjectIdToStringDeserializer;
import com.mycompany.myapp.json.StringToObjectIdSerializer;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent Database Reference / Foreign Key to
 * {@link #getRef()} collection...
 */
@Data
@NoArgsConstructor
public class RefType implements Serializable {

    private static final long serialVersionUID = 1L;

    public static enum RefTo {
        User,category,item
    }


    @JsonSerialize(using = StringToObjectIdSerializer.class)
    @JsonDeserialize(using = ObjectIdToStringDeserializer.class )
    @JsonProperty("_id")
    @Field("_id")
    private String id;

    @JsonProperty("_ref")
    @Field("_ref")
    private String ref;


    public RefType (String id, RefTo refTo) {
        setId(id);
        setRef(refTo.name());
    }

    public String toString() {
        ToStringBuilder tsb = new ToStringBuilder(this);
        tsb.append("ref", ref);
        tsb.append("id",id);
        return tsb.toString();
    }
}

