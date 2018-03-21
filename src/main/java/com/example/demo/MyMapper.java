package com.example.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.ehl.dataselect.hbase.persistent.TravelRecord;

public class MyMapper extends Mapper<ImmutableBytesWritable, KeyValue,Text, IntWritable>
{
  private static final byte[] cf = "cf".getBytes();
  private static SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
  private Text mapKey = new Text();
  private final static IntWritable one = new IntWritable(1);
  private static Writer output=null;
  public void map(ImmutableBytesWritable key, KeyValue value, Mapper<ImmutableBytesWritable, KeyValue,Text, IntWritable>.Context context)
    throws IOException, InterruptedException
  {
    try
    {
      byte[] column = value.getValue();
      TravelRecord tr = TravelRecord.fromBytes(column);
      TrPlateEsBean resultCar = TR2Result.getResultCar(tr, false, false);
     // System.out.println(resultCar);
		String yyyyMMdd =format.format(new Date(resultCar.getTimestamp()));
		mapKey.set(yyyyMMdd);
		IOUtils.write(resultCar.toString()+"\n", output);
		context.write(mapKey,one);
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }
@Override
protected void cleanup(Mapper<ImmutableBytesWritable, KeyValue, Text, IntWritable>.Context context)
		throws IOException, InterruptedException {
	// TODO Auto-generated method stub
	output.close();
	System.out.println("guanbi");
	super.cleanup(context);
}
@Override
protected void setup(Mapper<ImmutableBytesWritable, KeyValue, Text, IntWritable>.Context context)
		throws IOException, InterruptedException {
	output=new FileWriter(new File("F:\\guoche\\"+context.getJobID()));
	System.out.println("qidong");
	super.setup(context);
}

  
  

}