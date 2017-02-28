package nz.co.validatemynumber.bankaccount;

public class AlgorithmAValidator extends BankAccountValidator
{
    private static final int[] WEIGHT_FACTORS = {0, 0, 6, 3, 7, 9, 0, 0, 10, 5, 8, 4, 2, 1, 0, 0, 0, 0};
    private static final int MODULUS = 11;
    
    @Override
    public boolean isValid(String bankCode, String branch, String account, String suffix)
    {
        String bankAccount = bankCode.concat(branch).concat(account).concat(suffix);
        int sum = calculateSum(WEIGHT_FACTORS, bankAccount);
        int remainder = sum % MODULUS;
        return remainder == 0;
    }
}
