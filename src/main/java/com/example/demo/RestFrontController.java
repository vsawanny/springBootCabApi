package com.example.demo;

import com.example.demo.com.example.demo.model.Cabs;
import com.example.demo.entities.CabEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cabs")
public class RestFrontController {

    List<Cabs> listOfCabs=new ArrayList<>();
    @Autowired
    TransactionDAO tdo;

    @GetMapping(value = "/getAvailableCabs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        List<CabEntity> getOriginalPostageRequestByTransactionId(@RequestParam String areaCode) throws Exception{

     List<CabEntity>  cabs= tdo.getAllCabs(areaCode);

return cabs;

    }

    @PostMapping(value = "/addNewCabs" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Cabs addNewCabs(@RequestBody Cabs cabs)
    {
        Cabs cab=tdo.addNewCab(cabs);

        return cab;
    }

    @GetMapping(value="/bookAcab", consumes =MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    String bookCabs(@RequestParam String cabId)
    {
String returnString = tdo.deleteCab(cabId);
return returnString;
    }


}
