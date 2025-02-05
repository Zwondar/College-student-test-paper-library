package backs.back.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.DatabaseMetaData;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Study {
    private short id;
    private String school;
    private String college;
    private String discipline;
    private String name;
    private String time;
    private String UpDataTime;
    private String url;
    private String grade;
}
