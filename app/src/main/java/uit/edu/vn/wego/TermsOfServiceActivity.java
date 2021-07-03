package uit.edu.vn.wego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TermsOfServiceActivity extends AppCompatActivity {

    private TextView terms_of_service_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_service);
        terms_of_service_textview = findViewById(R.id.terms_of_service_textview);
        terms_of_service_textview.setText("Terms and Conditions of Use\n" +
                "1. Terms\n" +
                "By accessing this Website, accessible from wego.vn, you are agreeing to be bound by these Website Terms and Conditions of Use and agree that you are responsible for the agreement with any applicable local laws. If you disagree with any of these terms, you are prohibited from accessing this site. The materials contained in this Website are protected by copyright and trade mark law.\n" +
                "\n" +
                "2. Use License\n" +
                "Permission is granted to temporarily download one copy of the materials on weGO's Website for personal, non-commercial transitory viewing only. This is the grant of a license, not a transfer of title, and under this license you may not:\n" +
                "\n" +
                "modify or copy the materials;\n" +
                "use the materials for any commercial purpose or for any public display;\n" +
                "attempt to reverse engineer any software contained on weGO's Website;\n" +
                "remove any copyright or other proprietary notations from the materials; or\n" +
                "transferring the materials to another person or \"mirror\" the materials on any other server.\n" +
                "This will let weGO to terminate upon violations of any of these restrictions. Upon termination, your viewing right will also be terminated and you should destroy any downloaded materials in your possession whether it is printed or electronic format. These Terms of Service has been created with the help of the Terms Of Service Generator.\n" +
                "\n" +
                "3. Disclaimer\n" +
                "All the materials on weGO’s Website are provided \"as is\". weGO makes no warranties, may it be expressed or implied, therefore negates all other warranties. Furthermore, weGO does not make any representations concerning the accuracy or reliability of the use of the materials on its Website or otherwise relating to such materials or any sites linked to this Website.\n" +
                "\n" +
                "4. Limitations\n" +
                "weGO or its suppliers will not be hold accountable for any damages that will arise with the use or inability to use the materials on weGO’s Website, even if weGO or an authorize representative of this Website has been notified, orally or written, of the possibility of such damage. Some jurisdiction does not allow limitations on implied warranties or limitations of liability for incidental damages, these limitations may not apply to you.\n" +
                "\n" +
                "5. Revisions and Errata\n" +
                "The materials appearing on weGO’s Website may include technical, typographical, or photographic errors. weGO will not promise that any of the materials in this Website are accurate, complete, or current. weGO may change the materials contained on its Website at any time without notice. weGO does not make any commitment to update the materials.\n" +
                "\n" +
                "6. Links\n" +
                "weGO has not reviewed all of the sites linked to its Website and is not responsible for the contents of any such linked site. The presence of any link does not imply endorsement by weGO of the site. The use of any linked website is at the user’s own risk.\n" +
                "\n" +
                "7. Site Terms of Use Modifications\n" +
                "weGO may revise these Terms of Use for its Website at any time without prior notice. By using this Website, you are agreeing to be bound by the current version of these Terms and Conditions of Use.\n" +
                "\n" +
                "8. Your Privacy\n" +
                "Privacy Policy for weGO\n" +
                "At weGO, accessible from weGO.vn, one of our main priorities is the privacy of our visitors. This Privacy Policy document contains types of information that is collected and recorded by weGO and how we use it.\n" +
                "\n" +
                "If you have additional questions or require more information about our Privacy Policy, do not hesitate to contact us.\n" +
                "\n" +
                "This Privacy Policy applies only to our online activities and is valid for visitors to our website with regards to the information that they shared and/or collect in weGO. This policy is not applicable to any information collected offline or via channels other than this website. Our Privacy Policy was created with the help of the Privacy Policy Generator.\n" +
                "\n" +
                "Consent\n" +
                "By using our website, you hereby consent to our Privacy Policy and agree to its terms.\n" +
                "\n" +
                "Information we collect\n" +
                "The personal information that you are asked to provide, and the reasons why you are asked to provide it, will be made clear to you at the point we ask you to provide your personal information.\n" +
                "\n" +
                "If you contact us directly, we may receive additional information about you such as your name, email address, phone number, the contents of the message and/or attachments you may send us, and any other information you may choose to provide.\n" +
                "\n" +
                "When you register for an Account, we may ask for your contact information, including items such as name, company name, address, email address, and telephone number.\n" +
                "\n" +
                "How we use your information\n" +
                "We use the information we collect in various ways, including to:\n" +
                "\n" +
                "Provide, operate, and maintain our website\n" +
                "Improve, personalize, and expand our website\n" +
                "Understand and analyze how you use our website\n" +
                "Develop new products, services, features, and functionality\n" +
                "Communicate with you, either directly or through one of our partners, including for customer service, to provide you with updates and other information relating to the website, and for marketing and promotional purposes\n" +
                "Send you emails\n" +
                "Find and prevent fraud\n" +
                "Log Files\n" +
                "weGO follows a standard procedure of using log files. These files log visitors when they visit websites. All hosting companies do this and a part of hosting services' analytics. The information collected by log files include internet protocol (IP) addresses, browser type, Internet Service Provider (ISP), date and time stamp, referring/exit pages, and possibly the number of clicks. These are not linked to any information that is personally identifiable. The purpose of the information is for analyzing trends, administering the site, tracking users' movement on the website, and gathering demographic information.\n" +
                "\n" +
                "Advertising Partners Privacy Policies\n" +
                "You may consult this list to find the Privacy Policy for each of the advertising partners of weGO.\n" +
                "\n" +
                "Third-party ad servers or ad networks uses technologies like cookies, JavaScript, or Web Beacons that are used in their respective advertisements and links that appear on weGO, which are sent directly to users' browser. They automatically receive your IP address when this occurs. These technologies are used to measure the effectiveness of their advertising campaigns and/or to personalize the advertising content that you see on websites that you visit.\n" +
                "\n" +
                "Note that weGO has no access to or control over these cookies that are used by third-party advertisers.\n" +
                "\n" +
                "Third Party Privacy Policies\n" +
                "weGO's Privacy Policy does not apply to other advertisers or websites. Thus, we are advising you to consult the respective Privacy Policies of these third-party ad servers for more detailed information. It may include their practices and instructions about how to opt-out of certain options.\n" +
                "\n" +
                "You can choose to disable cookies through your individual browser options. To know more detailed information about cookie management with specific web browsers, it can be found at the browsers' respective websites.\n" +
                "\n" +
                "CCPA Privacy Rights (Do Not Sell My Personal Information)\n" +
                "Under the CCPA, among other rights, California consumers have the right to:\n" +
                "\n" +
                "Request that a business that collects a consumer's personal data disclose the categories and specific pieces of personal data that a business has collected about consumers.\n" +
                "\n" +
                "Request that a business delete any personal data about the consumer that a business has collected.\n" +
                "\n" +
                "Request that a business that sells a consumer's personal data, not sell the consumer's personal data.\n" +
                "\n" +
                "If you make a request, we have one month to respond to you. If you would like to exercise any of these rights, please contact us.\n" +
                "\n" +
                "GDPR Data Protection Rights\n" +
                "We would like to make sure you are fully aware of all of your data protection rights. Every user is entitled to the following:\n" +
                "\n" +
                "The right to access – You have the right to request copies of your personal data. We may charge you a small fee for this service.\n" +
                "\n" +
                "The right to rectification – You have the right to request that we correct any information you believe is inaccurate. You also have the right to request that we complete the information you believe is incomplete.\n" +
                "\n" +
                "The right to erasure – You have the right to request that we erase your personal data, under certain conditions.\n" +
                "\n" +
                "The right to restrict processing – You have the right to request that we restrict the processing of your personal data, under certain conditions.\n" +
                "\n" +
                "The right to object to processing – You have the right to object to our processing of your personal data, under certain conditions.\n" +
                "\n" +
                "The right to data portability – You have the right to request that we transfer the data that we have collected to another organization, or directly to you, under certain conditions.\n" +
                "\n" +
                "If you make a request, we have one month to respond to you. If you would like to exercise any of these rights, please contact us.\n" +
                "\n" +
                "Children's Information\n" +
                "Another part of our priority is adding protection for children while using the internet. We encourage parents and guardians to observe, participate in, and/or monitor and guide their online activity.\n" +
                "\n" +
                "weGO does not knowingly collect any Personal Identifiable Information from children under the age of 13. If you think that your child provided this kind of information on our website, we strongly encourage you to contact us immediately and we will do our best efforts to promptly remove such information from our records." +
                "\n" +
                "9. Governing Law\n" +
                "Any claim related to weGO's Website shall be governed by the laws of vn without regards to its conflict of law provisions.");
    }
}