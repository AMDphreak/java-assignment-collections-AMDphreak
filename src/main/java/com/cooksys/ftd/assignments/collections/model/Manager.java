package com.cooksys.ftd.assignments.collections.model;

import com.cooksys.ftd.assignments.collections.util.MissingImplementationException;

import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

/**
 * TODO: Implement this class
 *  <br><br>
 *  A manager is a type of employee that can be a superior to another employee.
 *  <br>
 *  A manager should have a name, and, optionally, a manager superior to them.
 */
public class Manager implements Employee {

    String name;
    Manager manager;

    /**
     * TODO: Implement this constructor.
     *
     * @param name the name of the manager to be created
     */
    public Manager(String name) {
        this.name = name;
    }

    /**
     *  TODO: Implement this constructor.
     *
     * @param name the name of the manager to be created
     * @param manager the direct manager of the manager to be created
     */
    public Manager(String name, Manager manager) {
        this.name = name;
        this.manager = manager;
    }

    /**
     * TODO: Implement this getter.
     *
     * @return the name of the manager
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * TODO: Implement this getter.
     *
     * @return {@code true} if this manager has a manager, or {@code false} otherwise
     */
    @Override
    public boolean hasManager() {
        return (manager != null);
    }

    /**
     * TODO: Implement this getter.
     *
     * @return the manager of this manager, or {@code null} if it has none
     */
    @Override
    public Manager getManager() {
        return manager;
    }

    /**
     * TODO: Implement this method.
     *  <br><br>
     *  Retrieves the manager's chain of command as a {@code List<Manager>}, starting with their direct {@code Manager},
     *  followed by that {@code Manager}'s {@code Manager}, and so on, until the top of the hierarchy is reached.
     *  <br><br>
     *  The returned list should never be or contain {@code null}.
     *  <br><br>
     *  If the manager does not have a {@code Manager}, an empty
     *  {@code List<Manager>} should be returned.
     *
     * @return a {@code List<Manager>} that represents the manager's chain of command,
     */
    @Override
    public List<Manager> getChainOfCommand() {
        List<Manager> list = Collections.synchronizedList(new LinkedList<Manager>());
        Manager nextUp = manager; // init
        while (nextUp != null) {
        	list.add(nextUp);
        	nextUp = nextUp.manager;
        }
        return list;
    }

    // TODO: Does this class need custom .equals() and .hashcode() methods? If so, implement them here.
    
    @Override
    public boolean equals(Object o) {
    	if (o != null && o instanceof Manager) {
    		Manager m = (Manager) o;
    		if (m.hasManager()) {
    			// make sure both have manager
    			if (m.hasManager() && this.hasManager()) {
    				// compare manager, name
	    			if (m.getManager().equals(manager) && m.getName().equals(name)) {
	    				return true; // manager and name match
	    			} else {
	    				return false;
	    			}
    			} else {
    				// one does not have manager
    				return false;
    			}
    		} else {
    			// make sure both have no manager
    			if (!m.hasManager() && !this.hasManager()) {
	    			// no manager, check names
	    			if (m.getName().equals(name)) {
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
