package nz.co.validatemynumber.bankaccount;

public class AlgorithmGValidator extends BankAccountValidator
{
    private static final int[] WEIGHT_FACTORS = {0, 0, 0, 0, 0, 0, 0, 1, 3, 7, 1, 3, 7, 1, 0, 3, 7, 1};
    private static final int MODULUS = 10;
    
    @Override
    public boolean isValid(String bankCode, String branch, String account, String suffix)
    {
        String bankAccount = bankCode.concat(branch).concat(account).concat(suffix);
        
        int sum = 0;
        
        for (int i = 0; i < WEIGHT_FACTORS.length; i++)
        {
            int product = WEIGHT_FACTORS[i] * Integer.parseInt(bankAccount.substring(i, i + 1));
            double tens = product / 10d;
            int units = product % 10;
            int sum2 = (int)(tens + units);
            tens = sum2 / 10d;
            units = sum2 % 10;
            sum2 = (int)(tens + units);
            sum += sum2;
        }
        
        int remainder = sum % MODULUS;
        return remainder == 0;
    }
}
