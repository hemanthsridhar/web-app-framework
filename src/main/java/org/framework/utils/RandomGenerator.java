package org.framework.utils;

import java.util.Random;

public class RandomGenerator {
	
public int generateRandomNumber()
{
	Random random = new Random();
	int randomNumber = random.nextInt();		
    return randomNumber;
}

public int generateEightRandomNumbers() {
	Random rnd = new Random();
	int n = 1000000 + rnd.nextInt(9900000);
	return n;
}

public char generateCharacters(){
	Random r = new Random();
	char c = (char)(r.nextInt(26) + 'a');
	return c;
}
}
