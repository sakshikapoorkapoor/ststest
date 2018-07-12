package main;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class Test {
	
	public static void main(String[] args) {
		//
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		String s = "0000000000000323080A00000160361E7D40002DF0190610F80E3000DA0000140000001208EF00F00050001505C80045010100FC0008B50008B600064231EC180000CD3788CE0096430F4E44000002C700000000100070010B0000000160361E0810002DF0190610F80E3000DA0000140000001208EF00F00050001505C80045010100FC0008B50008B600064231EA180000CD3788CE0096430F4D44000002C700000000100070010B0000000160361D92E0002DF0190610F80E3000DA0000140000001208EF00F00050001505C80045010100FC0008B50008B600064231EC180000CD3788CE0096430F4D44000002C700000000100070010B0000000160361D1DB0002DF0190610F80E3000DA0000140000001208EF00F00050001505C80045010100FC0008B50009B600064231EC180000CD3788CE0096430F4C44000002C700000000100070010B0000000160361CA880002DF0190610F80E3000DA0000140000001208EF00F00050001505C80045010100FC0008B50009B600064231EB180000CD3788CE0096430F4C44000002C700000000100070010B0000000160361C3350002DF0190610F80E3000DA0000140000001208EF00F00050001505C80045010100FC0008B50009B600064231EC180000CD3788CE0096430F4E44000002C700000000100070010B0000000160361BBE20002DF0190610F80E3000DA0000140000001208EF00F00050001505C80045010100FC0008B50008B600064231E7180000CD3788CE0096430F4D44000002C700000000100070010B0000000160361B48F0002DF0190610F80E3000DA0000140000001208EF00F00050001505C80045010100FC0008B50008B600064231EC180000CD3788CE0096430F4D44000002C700000000100070010B0000000160361AD3C0002DF0190610F80E3000DA0000140000001208EF00F00050001505C80045010100FC0008B50008B600064231EC180000CD3788CE0096430F4C44000002C700000000100070010B0000000160361A5E90002DF0190610F80E3000DA0000140000001208EF00F00050001505C80045010100FC0008B50008B600064231EC180000CD3788CE0096430F4D44000002C700000000100070010B000A0000A5BB"+"7";
		String codeStr = s.substring(s.length() - 2);
		int code=0;
		if(Character.isLetter(codeStr.charAt(0)) )
		{
			 code = Integer.parseInt(s.substring(s.length() - 1));
		}
		else{
			 code = Integer.parseInt(s.substring(s.length() - 2));
		}
		System.out.println("code: "+code);
		
		switch (code) {

		case 1:
			System.out.println("Innova device data rec1");

		case 7:
			System.out.println("Innova device data rec7");
		
			
	}

}}
