package org.example;

import org.junit.Test;

import java.util.Arrays;

public class ThirdPartTest {
    @Test
    public void givenClient1_whenServerResponds_thenCorrect() throws Exception {
        byte[] poisonPill =
                {19, 4, 6, 24, 124, -86, -47, -121, -48, 74, 0, 0, 0, 24, -128, -97, 0, 0, 0, 10, 0, 0, 0, 3, 83, 73, 112, 68, 49, 88, 75, 48, 51, 98, 115, 106, 85, 113, 112, 97, 121, 73, 103, 110, 111, 65, 61, 61, -84, 68, 0, 0, 0, 32, -15, -121, -93, 88, 104, -46, 30, 54, 28, -32, 122, -18, 50, -2, 55, -19, 78, 80, 17, -18, 114, 59, 66, 115, 5, -98, 124, 105, 48, -34, 125, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
;

        StoreClientTCP client1 = new StoreClientTCP();
        client1.startConnection("localhost", 2305);

        FakeNetwork f = new FakeNetwork();


        client1.sendPackage(f.generate());
        client1.receive();
        client1.sendPackage(f.generate());
        client1.receive();
        client1.sendPackage(poisonPill);
        client1.receive();

        StoreClientTCP client2 = new StoreClientTCP();
        client2.startConnection("localhost", 2305);
        client2.sendPackage(f.generate());
        client2.receive();

        client1.sendPackage(f.generate());
        client1.receive();


        client1.stopConnection();
        client2.stopConnection();




    }

    @Test
    public void givenClient2_whenServerResponds_thenCorrect() throws Exception {
        StoreClientTCP client2 = new StoreClientTCP();
        client2.startConnection("localhost", 2305);
        FakeNetwork f = new FakeNetwork();
        byte[] testPackagePackaged = f.generate();
        client2.sendPackage(testPackagePackaged);
        System.out.println("Received(at client2) : " +  Arrays.toString(client2.receive()));
        byte[] testPackagePackaged2 = f.generate();
        client2.sendPackage(testPackagePackaged2);
        System.out.println("Received(at client2) : " +  Arrays.toString(client2.receive()));
    }
}
