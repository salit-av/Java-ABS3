package XMLReader;

import Exceptions.*;
import jaxb.schema.generated.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;


public class XmlReader {
    private AbsDescriptor absDescriptor;
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "jaxb.schema.generated";

    public AbsDescriptor openXML(String filePath) throws referenceToCategoryThatIsntDefinedException, loanWhoseCustomerIsNotInSystemException, paymentRateIncorrectException, customersWithTheSameNameException {
        try {
            InputStream inputStream = new FileInputStream(new File(filePath));
            absDescriptor = deserializeFrom(inputStream);
        }
        catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

        if(referenceToCategoryThatIsNotDefined()){
            throw new referenceToCategoryThatIsntDefinedException();
        }

        if(loanWhoseCustomerIsNotInSystem()){
            throw new loanWhoseCustomerIsNotInSystemException();
        }

        if(paymentRateIncorrect()){
            throw new paymentRateIncorrectException();
        }

        if(customersWithTheSameName()){
            throw new customersWithTheSameNameException();
        }
        return absDescriptor;
    }

    private AbsDescriptor deserializeFrom(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return  (AbsDescriptor) u.unmarshal(inputStream);
    }

    public boolean referenceToCategoryThatIsNotDefined() {
        AbsLoans absLoans = absDescriptor.getAbsLoans();
        for(AbsLoan absLoan: absLoans.getAbsLoan()){
            if(!inCategories(absLoan.getAbsCategory())){
                return true;
            }
        }
        return false;
    }

    public boolean inCategories(String absCategory) {
        AbsCategories absCategories = absDescriptor.getAbsCategories();
        for(String category: absCategories.getAbsCategory()){
            if(category.equals(absCategory)){
                return true;
            }
        }
        return false;
    }

    public boolean loanWhoseCustomerIsNotInSystem() {
        AbsLoans absLoans = absDescriptor.getAbsLoans();
        for(AbsLoan absLoan: absLoans.getAbsLoan()){
            if(!customerInSystem(absLoan.getAbsOwner())){
                return true;
            }
        }
        return false;
    }

    public boolean customerInSystem(String absOwner) {
        AbsCustomers absCustomers = absDescriptor.getAbsCustomers();
        for(AbsCustomer customer: absCustomers.getAbsCustomer()){
            if(customer.getName().equals(absOwner)){
                return true;
            }
        }
        return false;
    }

    public boolean paymentRateIncorrect() {
        AbsLoans absLoans = absDescriptor.getAbsLoans();
        for(AbsLoan absLoan: absLoans.getAbsLoan()){
            if(absLoan.getAbsTotalYazTime() % absLoan.getAbsPaysEveryYaz() != 0){  // There is a division left
                return true;
            }
        }
        return false;
    }

    public boolean customersWithTheSameName() {
        AbsCustomers absCustomers = absDescriptor.getAbsCustomers();
        Set<String> allNames = new HashSet<>();
        for (AbsCustomer customer : absCustomers.getAbsCustomer()) {
            if (!allNames.add(customer.getName()))
                return true;
        }
        return false;
    }

}



//engine\src\resources\ex1-big.xml
//engine\src\resources\ex1-small.xml