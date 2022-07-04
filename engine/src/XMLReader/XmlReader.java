package XMLReader;

import AllParticipants.Categories;
import AllParticipants.Loan.Loans;
import Exceptions.loansWithTheSameNameException;
import Exceptions.paymentRateIncorrectException;
import Exceptions.referenceToCategoryThatIsntDefinedException;
import jaxb.schema.generated.AbsDescriptor;
import jaxb.schema.generated.AbsLoan;
import jaxb.schema.generated.AbsLoans;

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

    public AbsDescriptor openXML(String filePath, Categories allCategories, Loans allLoans) throws referenceToCategoryThatIsntDefinedException, paymentRateIncorrectException, loansWithTheSameNameException {
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
            throw new loansWithTheSameNameException();
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

    public String addLoan(String cusName, String id, String category, int capital, int totalYazTime, int paysEveryYaz, int internistPerPayment, Loans allLoans, Categories allCategories) {
        boolean categoryBool = categoryThatIsNotDefined(category,allCategories);
        boolean paymentBool = paymentRate(totalYazTime, paysEveryYaz);
        boolean idBool = loanWithTheSameID(id, allLoans);
        if(categoryBool && paymentBool && !idBool){
            return "true";
        }
        else if(!categoryBool){
            return "There is no such category in the system";
        }
        else if (!paymentBool){
            return "The rate of payments is not fully divided";
        }
        else {
            return "There is a loan with the id name in the system";
        }
    }

    public boolean categoryThatIsNotDefined(String category, Categories allCategories) {
        return allCategories.getCategories().contains(category);
    }

    public boolean paymentRate(int totalYazTime, int paysEveryYaz) {
        return totalYazTime % paysEveryYaz == 0;
    }

    public boolean loanWithTheSameID(String id, Loans allLoans) {
        return allLoans.getLoans().containsKey(id);
    }
}
