package nz.co.validatemynumber.bankaccount;

public class AlgorithmDValidator extends BankAccountValidator
{
    private static final int[] WEIGHT_FACTORS = {0, 0, 0, 0, 0, 0, 0, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0, 0};
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
