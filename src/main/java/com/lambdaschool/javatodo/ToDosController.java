package com.lambdaschool.ToDosController;

import com.lambdaschool.h2crudsnacks.models.Customer;
import com.lambdaschool.h2crudsnacks.models.Snack;
import com.lambdaschool.h2crudsnacks.models.VendingMachine;
import com.lambdaschool.h2crudsnacks.repository.CustomerRepository;
import com.lambdaschool.h2crudsnacks.repository.SnackRepository;
import com.lambdaschool.h2crudsnacks.repository.VendingMachineRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(value = "Crudy ToDos Application", description = "The classic ToDos Application in CRUD")
@RestController
@RequestMapping(path = {}, produces = MediaType.APPLICATION_JSON_VALUE)
public class ToDosController
{
    @Autowired
    CustomerRepository custrepos;

    @Autowired
    SnackRepository snackrepos;

    @Autowired
    VendingMachineRepository vendingrepos;
//
//    GET /users - returns all the users
//
//    GET /todos - return all the todos
//
//    GET /users/userid/{userid} - return the user based off of the user id
//
//    GET /users/username/{username} - return the user based off of the user name
//
//    GET /todos/todoid/{todoid} - return the todo based off of the todo id
//
//    GET /todos/users - return a listing of the todos with its assigned user's name
//
//    GET /todos/active - return a listing of the todos not yet completed. // adding this is now a stretch goal
//
//    POST /users - adds a user
//
//    POST /todos - adds a todo
//
//    PUT /users/userid/{userid} - updates a user based on userid
//
//    PUT /todos/todoid/{todoid} - updates a todo based on todoid
//
//    DELETE /users/userid/{userid} - Deletes a user based off of their userid and deletes all their associated todos
//
//    DELETE /todos/todoid/{todoid} - deletes a todo based off its todoid


    @ApiOperation(value = "list All Customers", response = List.class)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "Successfully recetrieve list"),
                    @ApiResponse(code = 401, message = "You are not authorized to the view the resource"),
                    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            })
    @GetMapping("/customer")
    public List<Customer> allcust()
    {
        return custrepos.findAll();
    }

    @ApiOperation(value = "Customer based off of customer id", response = Customer.class)
    @GetMapping("/customer/id/{id}")
    public Customer findCustId(
            @ApiParam(value = "This is the customer you seek", required = true) @PathVariable long id)
    {
        var foundCust = custrepos.findById(id);
        if (foundCust.isPresent())
        {
            return foundCust.get();
        }
        else
        {
            return null;
        }
    }

    @GetMapping("/customer/name/{name}")
    public Customer findCustomer(@PathVariable String name)
    {
        var foundCust = custrepos.findByName(name);
        if (foundCust != null)
        {
            return foundCust;
        }
        else
        {
            return null;
        }
    }

    @PostMapping("/customer")
    public Customer newCustomer(@RequestBody Customer customer) throws URISyntaxException
    {
        return custrepos.save(customer);
    }

    @PutMapping("/customer/id/{id}")
    public Customer changeCust(@RequestBody Customer newcust, @PathVariable long id) throws URISyntaxException
    {
        Optional<Customer> updateCust = custrepos.findById(id);
        if (updateCust.isPresent())
        {
            newcust.setId(id);
            custrepos.save(newcust);

            return newcust;
        }
        else
        {
            return null;
        }
    }

    @DeleteMapping("/customer/id/{id}")
    public Customer deleteCustomer(@PathVariable long id)
    {
        var foundCust = custrepos.findById(id);
        if (foundCust.isPresent())
        {
            custrepos.deleteById(id);
            return foundCust.get();
        }
        else
        {
            return null;
        }
    }

    @GetMapping("/snack")
    public List<Snack> allsnacks()
    {
        return snackrepos.findAll();
    }

    @GetMapping("/snack/vending")
    public List<Object[]> vendingSnacks()
    {
        return snackrepos.vendingSnacks();
    }

    @DeleteMapping("/snack/{id}")
    public void deleteSnack(@PathVariable Long id)
    {
        snackrepos.deleteById(id);
    }

    // GET    /vending - returns all vending machines *
    // GET    /vending/id/{id} - returns vending machine based on id *
    // GET    /vending/name/{name} - returns vending machine based on name *
    // POST   /vending - adds a vending machine *
    // PUT    /vending/id/{} - updates vending machine based on id *
    // DELETE /vending/{id} - delete vending machine based on id
    // since vending machine is returning a list of snacks, return value must be list

    @GetMapping("/vending")
    public List<VendingMachine> allvending()
    {
        return vendingrepos.findAll();
    }

    @GetMapping("/vending/id/{id}")
    public List<VendingMachine> getVendId(@PathVariable long id)
    {
        return vendingrepos.findById(id).stream().collect(Collectors.toList());
    }

    @GetMapping("/vending/{name}")
    public List<VendingMachine> namedvending(@PathVariable String name)
    {
        return vendingrepos.findByName(name);
    }

    @PostMapping("/vending")
    public VendingMachine newVending(@RequestBody VendingMachine vendingMachine) throws URISyntaxException
    {
        return vendingrepos.save(vendingMachine);
    }

    @PutMapping("/vending/id/{id}")
    public List<VendingMachine> changeVending(@RequestBody VendingMachine newVending, @PathVariable long id) throws URISyntaxException
    {
        Optional<VendingMachine> updatedVending = vendingrepos.findById(id);
        if (updatedVending.isPresent())
        {
            newVending.setId(id);
            vendingrepos.save(newVending);

            return java.util.Arrays.asList(newVending);
        }
        else
        {
            return updatedVending.stream().collect(Collectors.toList());
        }
    }

    @DeleteMapping("/vending/{id}")
    public List<VendingMachine> deleteVendingMachine(@PathVariable long id)
    {
        List<VendingMachine> rmvending = vendingrepos.findById(id).stream().collect(Collectors.toList());
        vendingrepos.deleteById(id);
        return rmvending;
    }
}