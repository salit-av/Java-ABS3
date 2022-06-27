package XMLReader;

import AllParticipants.Categories;
import AllParticipants.Loan.Loan;
import AllParticipants.Loan.Loans;
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

    public AbsDescriptor openXML(String filePath, Categories allCategories, Loans allLoans) throws referenceToCategoryThatIsntDefinedException, loanWhoseCustomerIsNotInSystemException, paymentRateIncorrectException, customersWithTheSameNameException {
        try {
            InputStream inputStream = new FileInputStream(new File(filePath));
            absDescriptor = deserializeFrom(inputStream);
        }
        catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

        if(referenceToCategoryThatIsNotDefined(allCategories)){
            throw new referenceToCategoryThatIsntDefinedException();
        }

        if(paymentRateIncorrect()){
            throw new paymentRateIncorrectException();
        }

        if(loansWithTheSameName(allLoans)){
            throw new customersWithTheSameNameException();
        }
        return absDescriptor;
    }



    private AbsDescriptor deserializeFrom(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return  (AbsDescriptor) u.unmarshal(inputStream);
    }

    public boolean referenceToCategoryThatIsNotDefined(Categories allCategories) {
        AbsLoans absLoans = absDescriptor.getAbsLoans();
        for(AbsLoan absLoan: absLoans.getAbsLoan()){
            if(!inCategories(absLoan.getAbsCategory(), allCategories)){
                return true;
            }
        }
        return false;
    }

    public boolean inCategories(String absCategory, Categories allCategories) {
        return absDescriptor.getAbsCategories().getAbsCategory().contains(absCategory) || allCategories.getCategories().contains(absCategory);
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

    public boolean loansWithTheSameName(Loans allLoans) {
        AbsLoans absLoans = absDescriptor.getAbsLoans();
        Set<String> allNames = new HashSet<>();
        for (AbsLoan loan : absLoans.getAbsLoan()) {
            if (!allNames.add(loan.getId()))
                return true;
        }
        for (String id : allLoans.getLoans().keySet()) {
            if (!allNames.add(id))
                return true;
        }
        return false;
    }
}
