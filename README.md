# ExcelAnalyzer
read data from excel by poi. orm to java object.  analyze the object data to generate report for user.

You should **define meta** like

new Meta(fieldName, cellIndexs, fieldClass, convert)

For example :
    new Meta("age", Lists.newArrayList(3), Integer.class, new IntegerConvert())
    
    
For more detail info , you can check TestConvert.java
