package uk.gov.hmcts.reform.ccd.client.model.metadatafields.definition;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class AccessControlList implements Serializable {

    @JsonAlias("role")
    @JsonProperty("accessProfile")
    private String accessProfile;
    private Boolean create;
    private Boolean read;
    private Boolean update;
    private Boolean delete;

    @Override
    public String toString() {
        return "ACL{"
                + "accessProfile='" + accessProfile + '\''
                + ", crud=" + (getCreate() ? "C" : "") + (getRead() ? "R" : "")
                + (getUpdate() ? "U" : "") + (getDelete() ? "D" : "")
                + '}';
    }
}
