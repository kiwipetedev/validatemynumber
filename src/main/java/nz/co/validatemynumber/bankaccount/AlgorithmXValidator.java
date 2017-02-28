package nz.co.validatemynumber.bankaccount;

public class AlgorithmXValidator extends BankAccountValidator
{
    @Override
    public boolean isValid(String bankCode, String branch, String account, String suffix)
    {
        return true;
    }
}
