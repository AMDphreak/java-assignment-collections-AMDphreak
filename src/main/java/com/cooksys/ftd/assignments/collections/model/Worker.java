package com.cooksys.ftd.assignments.collections.model;

import com.cooksys.ftd.assignments.collections.util.MissingImplementationException;

import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

/**
 * TODO: Implement this class
 *  <br><br>
 *  A worker is a type of employee that cannot be a superior to another employee.
 *  <br>
 *  A worker should have a name, and, optionally, a manager superior to them.
 */
public class Worker implements Employee {

	String name;
	Manager manager;
	
    /**
     * TODO: Implement this constructor.
     *
     * @param name the name of the worker to be created
     */
    public Worker(String name) {
        this.name = name;
    }

    /**
     *  TODO: Implement this constructor.
     *
     * @param name the name of the worker to be created
     * @param manager the direct manager of the worker to be created
     */
    public Worker(String name, Manager manager) {
    	this.name = name;
    	this.manager = manager;
    	//this.manager = (manager != null) ? manager : new Manager("");
    }

    /**
     * TODO: Implement this getter.
     *
     * @return the name of the worker
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * TODO: Implement this getter.
     *
     * @return {@code true} if this worker has a manager, or {@code false} otherwise
     */
    @Override
    public boolean hasManager() {
        return (manager != null);
    }

    /**
     * TODO: Implement this getter.
     *
     * @return the manager of this worker, or null if it has none
     */
    @Override
    public Manager getManager() {
    	return manager;
    }

    /**
     * TODO: Implement this method.
     *  <br><br>
     *  Retrieves the worker's chain of command as a {@code List<Manager>}, starting with their direct {@code Manager},
     *  followed by that {@code Manager}'s {@code Manager}, and so on, until the top of the hierarchy is reached.
     *  <br><br>
     *  The returned list should never be or contain {@code null}.
     *  <br><br>
     *  If the worker does not have a {@code Manager}, an empty
     *  {@code List<Manager>} should be returned.
     *
     * @return a {@code List<Manager>} that represents the worker's chain of command,
     */
    @Override
    public List<Manager> getChainOfCommand() {
        List<Manager> list = Collections.synchronizedList(new LinkedList<Manager>());
        if (this.hasManager()) {
        	Manager m = manager;
        	while (m != null) {
        		list.add(m);
        		m = m.getManager();
        	}
        }
        return list;
    }

    // TODO: Does this class need custom .equals() and .hashcode() methods? If so, implement them here.
    @Override
    public boolean equals(Object o) {
    	if (o != null && o instanceof Worker) {
    		Worker w = (Worker) o;
    		if (w.hasManager()) {
    			// make sure both have manager
    			if (w.hasManager() && this.hasManager()) {
    				// compare manager, name
	    			if (w.getManager().equals(manager) && w.getName().equals(name)) {
	    				return true; // manager and name match
	    			} else {
	    				return false;
	    			}
    			} else {
    				// one has manager, other does not have manager
    				return false;
    			}
    		} else {
    			// make sure both have no manager
    			if (!w.hasManager() && !this.hasManager()) {
	    			// no managers, check names
	    			if (w.getName().equals(name)) {
	    				return true;
	    			} else {
	    				return false;
	    			}
    			} else {
    				return false;
    			}
    		}
    	} else {
    		return false;
    	}
    }

    @Override
    public int hashCode() {
    	final int prime = 31;
    	int result = 1;
    	result = prime * result + name.hashCode();
    	if (manager != null) {
    		result = prime * result + manager.hashCode();
    	}
    	return result;
    }

    // TODO [OPTIONAL]: Consider adding a custom .toString() method here if you want to debug your code with System.out.println() statements
}
