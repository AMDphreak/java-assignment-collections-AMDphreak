package com.cooksys.ftd.assignments.collections.model;

import com.cooksys.ftd.assignments.collections.model.Employee;
import com.cooksys.ftd.assignments.collections.model.Manager;
import com.cooksys.ftd.assignments.collections.util.MissingImplementationException;

import java.util.*;

public class OrgChart {

    // TODO: this class needs to store employee data in private fields in order for the other methods to work as intended.
    //  Add those fields here. Consider how you want to store the data, and which collection types to use to make
    //  implementing the other methods as easy as possible. There are several different ways to approach this problem, so
    //  experiment and don't be afraid to change how you're storing your data if it's not working out!
	
	Set<Employee> org = Collections.synchronizedSet(new HashSet<Employee>(0));

    /**
     * TODO: Implement this method
     *  <br><br>
     *  Adds a given {@code Employee} to the {@code OrgChart}. If the {@code Employee} is successfully added, this
     *  method should return true. If the {@code Employee} was not successfully added, it should return false. In order
     *  to determine if and how an {@code Employee} can be added, refer to the following algorithm:
     *  <br><br>
     *  If the given {@code Employee} is already present in the {@code OrgChart}, do not add it and
     *  return false. Otherwise:
     *  <br><br>
     *  If the given {@code Employee} has a {@code Manager} and that {@code Manager} is not part of the
     *  {@code OrgChart} yet, add that {@code Manager} first and then add the given {@code Employee}, and return true.
     *  <br><br>
     *  If the given {@code Employee} has no {@code Manager}, but is a {@code Manager} itself, add it to the
     *  {@code OrgChart} and return true.
     *  <br><br>
     *  If the given {@code Employee} has no {@code Manager} and is not a {@code Manager} itself, do not add it
     *  and return false.
     *
     * @param e the {@code Employee} to add to the {@code OrgChart}
     * @return true if the {@code Employee} was added successfully, false otherwise
     */
    public boolean addEmployee(Employee e) {
    	// if employee is null
    	// or if orgchart has employee
        if (e == null || this.hasEmployee(e)) {
        	return false;
        }
        // or if e is a worker who has no manager
        if (e instanceof Worker && e.hasManager() == false) {
        	return false;
        }
        // otherwise, add employee, but add his manager and all recursive managers first
    	if (e.hasManager()) {
    		// recursively add managers until the top
    		this.addEmployee(e.getManager());
    	}
    	// finally, add employee
    	org.add(e);
    	return true;
    }

    /**
     * TODO: Implement this method
     *  <br><br>
     *  Checks if a given {@code Employee} has already been added to the {@code OrgChart}.
     *
     * @param e the {@code Employee} to search for
     * @return true if the {@code Employee} has been added to the {@code OrgChart}, false otherwise
     */
    public boolean hasEmployee(Employee e) {
        if (e == null) {
        	return false;
        } else {
        	return org.contains(e);
        }
    }

    /**
     * TODO: Implement this method
     *  <br><br>
     *  Retrieves all {@code Employee}s that have been added to the {@code OrgChart} as a flat {@code Set}. If no
     *  {@code Employee}s have been added to the {@code OrgChart} yet, the returned {@code Set} should be empty (not
     *  {@code null}!).
     *
     * @return all {@code Employee}s in the {@code OrgChart}, or an empty {@code Set} if no {@code Employee}s have
     *         been added to the {@code OrgChart}
     */
    public Set<Employee> getAllEmployees() {
    	// Had to look up how to create a shallow clone of this.
    	// Creating a new Set<Employee> s = Collections.synchronizedSet(org); did not work
    	// but I suspect Set<Employee> s = Collections.synchronizedSet(new HashSet<>(org)); return s;
    	// would have worked.
    	// Actually I just changed my return statement
    	return Collections.synchronizedSet(new HashSet<>(org));
    }

    /**
     * TODO: Implement this method
     *  <br><br>
     *  Retrieves all {@code Manager}s that have been added to the {@code OrgChart} as a flat {@code Set}. If no
     *  {@code Manager}s have been added to the {@code OrgChart}, the returned {@code Set} should be empty (not
     *  {@code null}!).
     *
     * @return all {@code Manager}s in the {@code OrgChart}, or an empty {@code Set} if no {@code Manager}s
     *         have been added to the {@code OrgChart}
     */
    public Set<Manager> getAllManagers() {
        Set<Manager> managers = Collections.synchronizedSet(new HashSet<Manager>(0));
        for (Employee e : org) {
        	if (e instanceof Manager) {
        		managers.add((Manager) e);
        		// if this manager has a manager, recursively add upper management
    			// transfer manager to iterator variable m
    			Manager m = (Manager) e;
    			while (m != null) {
    				managers.add(m);
    				m = m.getManager();
        		}
        	}
        }
    	return managers;
    }

    /**
     * TODO: Implement this method
     *  <br><br>
     *  Retrieves all of the direct subordinates of a given {@code Manager} as a flat {@code Set}.
     *  <br><br>
     *  These are the {@code Employee}s (both {@code Worker}s and {@code Manager}s) that return the given
     *  {@code Manager} when their {@code .getManager()} method is called.
     *  <br><br>
     *  If the given {@code Manager} does not have any subordinates in the
     *  {@code OrgChart}, or if the given {@code Manager} is itself not in the {@code OrgChart}, the returned
     *  {@code Set} should be empty, but not {@code null}.
     *
     * @param manager the {@code Manager} whose direct subordinates need to be returned
     * @return all {@code Employee}s in the {@code OrgChart} that have the given {@code Manager} as a direct
     *         parent, or an empty set if the {@code Manager} is not present in the {@code OrgChart}
     *         or if there are no subordinates for the given {@code Manager}
     */
    public Set<Employee> getDirectSubordinates(Manager manager) {
        Set<Employee> employees = Collections.synchronizedSet(new HashSet<Employee>(0));
        for (Employee e : org) {
        	if (e.hasManager()) {
        		if (e.getManager().equals(manager)) {
        			employees.add(e);
        		}
        	}
        }
        return employees;
    }
    

    /**
     * TODO: Implement this method
     *  <br><br>
     *  Retrieves a full representation of this {@code OrgChart} as a {@code Map} of {@code Manager} keys and
     *  {@code Set<Employee>} values. Every single {@code Manager} in the {@code OrgChart} should appear as a key in
     *  the returned {@code Map}, and for each {@code Manager} key, every {@code Employee} (both {@code Worker}s and
     *  {@code Manager}s) in the {@code OrgChart} whose direct manager is that key should be in the associated
     *  {@code Set<Employee>} value.
     *  <br><br>
     *  Like the other methods, the return value of this method should not be {@code null} or contain {@code null},
     *  either in its keys or values. An empty {@code Map} should be returned if the {@code OrgChart} is empty.
     *
     * @return a map in which the keys represent the parent {@code Manager}s in the
     *         {@code OrgChart}, and the each value is a set of the direct subordinates of the
     *         associated {@code Manager}, or an empty map if the {@code OrgChart} is empty.
     */
    public Map<Manager, Set<Employee>> getFullHierarchy() {
        Map<Manager, Set<Employee>> map = Collections.synchronizedMap(new HashMap<Manager, Set<Employee>>(0));
        for (Employee e : org) {
        	/* When encountering new manager, create new key and new set <key, set<>>.
        	 * 
        	 * When encountering an employee with manager already in the map, add employee to
        	 * the set.
        	 * 
        	 * When encountering an employee and manager is not in map, add employee's manager to map
        	 * with new set containing employee.
        	 */
        	
        	// If he has a manager, add his manager
        	/* Add Manager's Manager */
        	if (e.hasManager()) {
        		Manager m = e.getManager();
        		if (!map.containsKey(m)) { // Check for his manager
        			/* Manager doesn't exist so add his manager */
        			
        			// Add him to new employee Set:
        			Set<Employee> employees = Collections.synchronizedSet(new HashSet<Employee>(0));
        			employees.add(e);
        			map.put(m, employees); // Add his manager to map
        		} else {
        			map.get(m).add(e); // add him to his manager's list
        		}
			}
        	
        	/* Add Manager */
        	if (e instanceof Manager) {
        		if (!map.containsKey(e)) { // If he isn't in the map as a manager
        			Set<Employee> employees = Collections.synchronizedSet(new HashSet<Employee>(0));
        			map.put((Manager) e, employees); // add <manager, empty set>
        		}
        	}
        	if (e instanceof Worker) {
        		if (e.hasManager()) {
        			Manager m = e.getManager();
        			if (!map.containsKey(m)) {
        				Set<Employee> employees = Collections.synchronizedSet(new HashSet<Employee>(0));
        				employees.add(e);
        				map.put(m, employees);
        			}
        			// else add to the Set
        			map.get(m).add(e);
        		}
        		// else if a worker doesn't have a manager, just don't add it.
        	}
        }
        return map;
    }

}
