package com.apollo.controller.sellingController;

import com.apollo.entity.Bullet;
import com.apollo.payload.response.BulletResponse;
import com.apollo.service.BulletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/bullet/{product_id}")
public class BulletController {

    @Autowired
    private BulletService iBulletService;
    @PostMapping
    public ResponseEntity<BulletResponse> createBulletListOfProduct(@PathVariable Long product_id, @RequestBody List<String> bulletList){
        BulletResponse bulletResponse = new BulletResponse();
        List<Bullet> bulletLists = iBulletService.createBullet(bulletList,product_id);
        if(bulletLists != null){
            bulletResponse.setMessage("create bullet list successful");
            return new ResponseEntity<>(bulletResponse, HttpStatus.OK);
        } else {
            bulletResponse.setMessage("fail to create bullet for product");
            return new ResponseEntity<>(bulletResponse,HttpStatus.BAD_REQUEST);
        }
    }
}
