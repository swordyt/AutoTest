package com.autotest.run;
import org.junit.Rule;
import org.junit.Test;
import com.autotest.annotation.DataSource;
import com.autotest.core.DriverService;
import com.autotest.core.sendHttp;
import com.autotest.enums.DataType;
import com.autotest.enums.DriverType;
import com.autotest.entity.request.*;
public class run {
@Rule
public DriverService ds=new DriverService();
@Test
@DataSource(Drive=DriverType.excel,minmax=DataType.min)
public void set01(){
new sendHttp().send(new ReqEntity_login());
new sendHttp().send(new ReqEntity_Test01());
new sendHttp().send(new ReqEntity_Test02());
}
@Test
@DataSource(Drive=DriverType.excel,minmax=DataType.max)
public void set02(){
new sendHttp().send(new ReqEntity_login());
new sendHttp().send(new ReqEntity_Test01());
new sendHttp().send(new ReqEntity_Test02());
}
}